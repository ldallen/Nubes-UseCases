    var eb = new vertx.EventBus("http://localhost:8080/eventbus");
    var taskGrid = null;

    eb.onopen = function() {
        eb.registerHandler("taskList.update", function(msg) {

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
        })
        eb.send("task.register","");
    }

    function addTaskHandler(){

        var name = $( "input:text[name=taskname]" ).val();
        var description = $( "input:text[name=taskdescription]" ).val();
        var jsonStr = '{ "taskname":"' + name + '", "taskdescription":"' + description + '"}';
        var msg = JSON.parse(jsonStr);
        eb.send("task.add", msg);
        $( "input:text[name=taskname]").val('');
        $( "input:text[name=taskdescription]").val('');
    }

    function delTaskHandler(){

        var name = $( "input:text[name=taskname]" ).val();
        var description = $( "input:text[name=taskdescription]" ).val();
        var jsonStr = '{ "taskname":"' + name + '", "taskdescription":"' + description + '"}';
        var msg = JSON.parse(jsonStr);
        eb.send("task.del", msg);
        $( "input:text[name=taskname]").val('');
        $( "input:text[name=taskdescription]").val('');
    }