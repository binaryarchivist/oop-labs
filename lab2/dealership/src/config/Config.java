package config;

import autoservice.Autoservice;
import autoservice.employees.Dispatcher;
import autoservice.employees.Mechanic;

import common.bank.BankAccount;
import common.employee.Review;

import dealership.Dealership;
import dealership.car.Car;
import dealership.car.CarStatus;
import dealership.car.TestDrive;
import dealership.employees.Administrator;
import dealership.employees.Buyer;
import dealership.employees.Seller;


import static config.Constants.rand;
import static config.Generator.*;

import java.util.*;

public class Config {
    private final Autoservice autoservice = new Autoservice("8",
            "9-5",
            13,
            generateBankAccount(0, 100000, "Autoservice"));
    private final Dealership dealership = new Dealership("8",
            "9-5",
            13,
            generateBankAccount(0, 1000000, "Dealership"),
            new ArrayList<>(),
            new ArrayList<>());


    public void init() {

        Mechanic mechanic = new Mechanic("firstName", "lastName", 23, 10.5);
        Dispatcher dispatcher = new Dispatcher("firstName", "lastName", 20, 10);
        autoservice.hireEmployee(dispatcher);
        autoservice.hireEmployee(mechanic);

        // mock dealership.car
        Car mockCar = generateCar();

        Administrator admin = new Administrator("Cornel", "Nastas", 20, 20, dealership);
        // seller
        Seller seller = new Seller("Employee", "Seller", 20, 15, dealership);

        // customer
        Buyer buyer = generateCustomer();


        boolean flag = true;
        Scanner sc = new Scanner(System.in);


        while (flag) {
            System.out.println("1 - Customer tries to buy a car");
            System.out.println("2 - Dealership bank");
            System.out.println("3 - Sellers rating, reviews");

            int option = sc.nextInt();

            switch (option) {
                case 1 -> {
                    if (dealership.getCars().isEmpty()) {
                        System.out.println("Dealership has no available cars");
                        break;
                    }
                    ;
                    String description = "";
                    double stars;

                    System.out.println(dealership);

                    Car testCar = dealership.getCars().get(0);
                    TestDrive testDrive = buyer.testDrive(testCar, seller, 10, new Date());

                    Constants.timer.schedule(Constants.wrap(() -> buyer.finishTestDrive(testDrive)), 10);

                    int unluckyEvent = rand.nextInt(10);
                    if (unluckyEvent == 1) {
                        System.out.println("Car needs service, broke down during test drive!");
                        testCar.setStatus(CarStatus.SERVICE);
                        System.out.println(mechanic.getCurrentCar());
                        dispatcher.assignCar(testCar, mechanic);
// mechanic: time, price, experience : rating
                        int price = rand.nextInt(1000, 5000);
                        mechanic.repairCar(10);
                        if (price > 1000 && price < 2000) {
                            System.out.println("Good mechanic !");
                            dealership.getBankAccount().transfer(autoservice.getBankAccount(), price);
                        } else if (price > 2000 && price < 3000) {
                            System.out.println("Cheeky mechanic ?!");
                            dealership.getBankAccount().transfer(autoservice.getBankAccount(), price);
                        } else {
                            System.out.println("(Dealership) How come ? Are you out of your mind ?");
                            System.out.println("(Mechanic) Lets see again.");
                            int newPrice = rand.nextInt(1000, 3000);
                            if (newPrice > 1000 && newPrice < 2000) {
                                System.out.println("Good mechanic !");
                                dealership.getBankAccount().transfer(autoservice.getBankAccount(), price);
                            } else if (newPrice > 2000 && newPrice < 3000) {
                                System.out.println("Cheeky mechanic ?!");
                                dealership.getBankAccount().transfer(autoservice.getBankAccount(), price);
                            }
                        }

                        description = "He gave me a car with a defect, what could happen in the worst case scenario ???";
                        stars = 1;
                        seller.receiveReview(new Review(description, stars));
                        break;
                    }

                    int satisfaction = rand.nextInt(10);
                    if (satisfaction > 0 && satisfaction < 5) {
                        switch (rand.nextInt(4)) {
                            case 1 -> {
                                description = "Terrible person";
                                stars = 1;
                            }
                            case 2 -> {
                                description = "Doesn't know what he is supposed to do";
                                stars = 2;
                            }
                            case 3 -> {
                                description = "He didn't adhere to my specifications";
                                stars = 3;
                            }
                            default -> {
                                description = "I'd rather not talk";
                                stars = 4;
                            }
                        }

                        Review review = new Review(description, stars);
                        seller.receiveReview(review);
                        break;
                    }
                    switch (rand.nextInt(4, 10)) {
                        case 5 -> {
                            description = "Could use some lessons.";
                            stars = 5;
                        }
                        case 6 -> {
                            description = "I wish he'd listen more.";
                            stars = 6;
                        }
                        case 7 -> {
                            description = "Made me wait a whole lot before actually talking business.";
                            stars = 7;
                            buyer.buyCar(dealership.getCars().get(0), dealership.getBankAccount());
                            seller.sell(dealership.getCars().get(0));
                        }
                        case 8 -> {
                            description = "Overall pleasant experience.";
                            stars = 8;
                            buyer.buyCar(dealership.getCars().get(0), dealership.getBankAccount());
                            seller.sell(dealership.getCars().get(0));
                        }
                        case 9 -> {
                            description = "Nice suggestions, could be a bit more understanding.";
                            stars = 9;
                            seller.sell(dealership.getCars().get(0));
                            buyer.buyCar(dealership.getCars().get(0), dealership.getBankAccount());
                        }
                        default -> {
                            description = "Excellent experience, pleasure to work with such persons.";
                            stars = 10;
                            buyer.buyCar(dealership.getCars().get(0), dealership.getBankAccount());
                            seller.sell(dealership.getCars().get(0));
                        }
                    }

                    Review review = new Review(description, stars);
                    seller.receiveReview(review);

                }

                case 2 -> {
                    System.out.println(dealership.getBankAccount());
                }
                case 3 -> {
                    System.out.println(seller.reviews);
                    System.out.println(seller.getRating());
                }
                case 4 -> {

                }
                default -> flag = false;
            }
        }
    }

}