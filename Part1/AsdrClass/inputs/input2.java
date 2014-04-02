public class Car
{
  private String manufacturer;
  private String model;
  private int year;
  private int odometer;

  public Car()
  {
  }

  public void Drive(int kilometers)
  {
    this.odometer = this.odometer + kilometers;
  }
}
