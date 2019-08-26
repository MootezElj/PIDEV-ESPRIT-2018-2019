package utils;

public class TableViewFormat {
    private String id;
    private String lib;

    public TableViewFormat() {
    }

    public TableViewFormat(String id, String lib) {
        this.id = id;
        this.lib = lib;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    @Override
    public String toString() {
        return "TableViewFormat{" +
                "id='" + id + '\'' +
                ", lib='" + lib + '\'' +
                '}';
    }
}
