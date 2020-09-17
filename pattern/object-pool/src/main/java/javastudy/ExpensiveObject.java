package javastudy;

public class ExpensiveObject implements AutoCloseable {

  private int cost;

  public ExpensiveObject(int cost) {
    this.cost = cost;
  }

  int getCost() {
    return cost;
  }

  @Override
  public void close() throws Exception {
    this.cost = -1;
  }
}
