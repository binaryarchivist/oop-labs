package dealership.model;

import common.employee.Review;
import common.employee.EmployeeImpl;

import java.util.ArrayList;
import java.util.List;

public class Seller extends EmployeeImpl {

    private final Dealership dealership;

    public List<Review> reviews = new ArrayList<>();

    public Seller(String firstName, String lastName, int age, Dealership dealership) {
        super(firstName, lastName, age);
        this.dealership = dealership;
    }

    public void receiveReview(Review review) {
        reviews.add(review);
    }

    public double getRating() {
        double totalStars = reviews.stream().mapToDouble(review -> review.stars).sum();

        return totalStars / reviews.size();
    }

    public void sell(Car car) {
        car.status = CarStatus.SOLD;
        this.dealership.removeCar(car);
    }

    public void testDrive(Car car) {
        car.status = CarStatus.TEST_DRIVE;
    }

    public void returnCar(Car car) {
        car.status = CarStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "dealership=" + dealership +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}