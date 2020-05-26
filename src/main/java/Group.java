public class Group {
    private String nameOfGroup;
    private int idOfGroup;

    public Group(int id, String name) {

        this.idOfGroup = id;
        this.nameOfGroup = name;

    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public int getIdOfGroup() {
        return idOfGroup;
    }

    public void setIdOfGroup(int idOfGroup) {
        this.idOfGroup = idOfGroup;
    }
}
