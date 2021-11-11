public class Student {

    private String name;
    private String lastname;
    private int age;
    private String spec;

    public Student(String name, String lastname, int age, String spec) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.spec = spec;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", spec='" + spec + '\'' +
                '}';
    }
}
