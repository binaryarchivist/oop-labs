package dealership.model;

import common.bank.BankAccount;
import common.base.Building;
import dealership.model.Car;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Dealership extends Building {

    private final BankAccount bankAccount;
    private List<String> contact;

    private List<Car> cars;

    public Dealership(String workingHours, String schedule, int breakHour, BankAccount bankAccount, List<String> contact, List<Car> cars) {
        super(workingHours, schedule, breakHour);

        this.bankAccount = bankAccount;
        this.contact = contact;
        this.cars = cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public List<String> getContact() {
        return contact;
    }

    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    public List<Car> getCars() {
        return cars;
    }

    public String getCarsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Car car : cars) {
            sb.append(cars.indexOf(car) + 1)
                    .append(" - ")
                    .append(car.toString()).append(",\n");
        }

        sb.append("]");
        return sb.toString();
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Dealership{" +
                "workingHours='" + workingHours + '\'' +
                ", schedule='" + schedule + '\'' +
                ", breakHour=" + breakHour +
                ", isOpen=" + isOpen +
                ", bankAccount=" + bankAccount +
                ", contact=" + contact +
                ",\n cars=" + getCarsList() + '}';
    }
}