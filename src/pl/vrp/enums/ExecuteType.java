package pl.vrp.enums;

public enum ExecuteType {
	RANDOMIZE(0),
	DIR_EXECUTE(1),
	FILE_EXECUTE(2);
	
	private Integer typeId;
	
	private ExecuteType(Integer typeId){
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}
