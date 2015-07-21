    var eb = new vertx.EventBus("http://localhost:8080/eventbus");
    var taskGrid = null;

    eb.onopen = function(){
        var options = {"headers":{"action":"register"}};
        var jsonMsg = {"options":options,"body":{}};
        eb.send("web.tasks.service",jsonMsg, function(res){
            if(res!=null){
               updateList(res);
            }
        });
    }


    function getTasks(){
        var options = {"headers":{"action":"getTasks"}};
        var jsonMsg = {"options":options,"body":{}};
        eb.send("web.tasks.service",jsonMsg, function(res){
            if(res!=null){
                updateList(res);
            }
        });

    }


    function addTaskHandler(){

        var name = $( "input:text[name=taskname]" ).val();
        var description = $( "input:text[name=taskdescription]" ).val();
        var jsonStr = '{ "name":"' + name + '", "description":"' + description + '"}';
        var task = JSON.parse(jsonStr);
        var options = {"headers":{"action":"add"}};
        var jsonMsg = {"options":options,"body":{"task":task}};
        eb.send("web.tasks.service", jsonMsg, function(res){
            if(res!=null){
                updateList(res);
            }
        });
        $( "input:text[name=taskname]").val('');
        $( "input:text[name=taskdescription]").val('');
    }

    function delTaskHandler(){

        var name = $( "input:text[name=taskname]" ).val();
        var options = {"headers":{"action":"del"}};
        var jsonMsg = {"options":options,"body":{"name":name}};
        eb.send("web.tasks.service", jsonMsg, function(res){
            if(res!=null){
                updateList(res);
            }
        });
        $( "input:text[name=taskname]").val('');
        $( "input:text[name=taskdescription]").val('');
    }


    function updateList(msg){

        var json = JSON.parse(msg);
        var aaData=[];

        $.each(json, function(index, object) {
            var name = index;
            var desc = object;
            var data = {Name:name,Description:desc};
            aaData.push(data);
        });
        if(taskGrid) {
            $("#todolist").jqGrid('clearGridData',true);
        }
        else {
            taskGrid = $("#todolist").jqGrid({
                datatype: "local",
                height: 250,
                colNames:['Name','Description'],
                colModel:[
                    {name:'Name',index:'Name', width:200,editable:true},
                    {name:'Description',index:'Description', width:250, sortable:false, editable:true,edittype:"textarea", editoptions:{rows:"2",cols:"20"}}
                ],
                pager: '#pager',
                multiselect: false,
                onSelectRow: function(id){
                    var rowData =  $("#todolist").jqGrid('getRowData',id);
                    $( "input:text[name=taskname]").val(rowData.Name);
                    $( "input:text[name=taskdescription]").val(rowData.Description);
                },
                caption: "Todo-List"
            });
        }
        for (var i=0; i<aaData.length; i++)
        {
            $("#todolist").jqGrid('addRowData',i+1,aaData[i]);
        }
        $("#todolist").setGridParam({sortname:'Name', sortorder: 'asc'}).trigger('reloadGrid');
    }