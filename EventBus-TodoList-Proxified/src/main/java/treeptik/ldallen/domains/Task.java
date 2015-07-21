package treeptik.ldallen.domains;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {

	private String name;
	private String description;

	public Task() {
		this.name = "taskName";
		this.description = "taskDescription";
	}

	public Task(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "=" + description;
	}
}
