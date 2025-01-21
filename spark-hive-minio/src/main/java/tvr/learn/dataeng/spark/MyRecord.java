package tvr.learn.dataeng.spark;


public class MyRecord implements java.io.Serializable {
    public MyRecord(int id, String data) {
        this.id = id;
        this.data = data;
    }

    private int id;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}