package Steps;

public class CommonSteps<T, TD> {
    public TD data;

    public T setData(TD data) {
        this.data = data;
        return (T) this;
    }

}
