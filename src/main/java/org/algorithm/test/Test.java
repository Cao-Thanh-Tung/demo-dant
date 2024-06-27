package org.algorithm.test;

import org.algorithm.*;
import org.algorithm.constraint.CapacityConstraint;
import org.algorithm.constraint.IConstraint;
import org.algorithm.constraint.TimeWindowConstraint;
import org.algorithm.distancetimematrix.DistanceTimeOsmMatrix;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;
import org.algorithm.objective.IObjective;
import org.algorithm.objective.MaxServeAbleOrderObjective;
import org.algorithm.objective.MinDistanceObjective;
import org.algorithm.objective.MinDurationObjective;
import org.algorithm.strategy.IStrategy;
import org.algorithm.strategy.heuristic.ShuffleRoundRobinStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        IDistanceTimeMatrix distanceTimeMatrix = new DistanceTimeOsmMatrix();
        IConstraint[] constraints = {
                new TimeWindowConstraint(distanceTimeMatrix),
                new CapacityConstraint()
        };
        IObjective[] objectives = {
                new MaxServeAbleOrderObjective(),
                new MinDistanceObjective(),
                new MinDurationObjective()
        };
        AlgorithmInput input = getInputFromFile("dataset/5_20/5_20_1.txt", distanceTimeMatrix);
        AlgorithmConfig config = new AlgorithmConfig();
        config.setConstraints(constraints);
        config.setObjectives(objectives);
        config.setNumShuffle(20);
        IStrategy strategy = new ShuffleRoundRobinStrategy();
        Solution solution = strategy.createSolution(input, config);
        solution.printSolution();
    }

    public static AlgorithmInput getInputFromFile(String fileName, IDistanceTimeMatrix distanceTimeMatrix) {
        Vehicle[] vehicles = null;
        Order[] orders = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // Đọc dòng đầu tiên để lấy số lượng vehicles và orders
            String firstLine = bufferedReader.readLine();
            String[] parts = firstLine.split(" ");
            int vNum = Integer.parseInt(parts[0]);
            int oNum = Integer.parseInt(parts[1]);

            // Đọc thông tin về vehicles
            vehicles = new Vehicle[vNum];
            for (int i = 0; i < vNum; i++) {
                String line = bufferedReader.readLine();
                parts = line.split(" ");
                double capacity = Double.parseDouble(parts[0]);
                int parkId = Integer.parseInt(parts[1]);
                Position park = null;
                for (int j = 0; j < Position.getParks().length; j++) {
                    if(Position.getParks()[j].getId() == parkId){
                        park = Position.getParks()[j];
                        break;
                    }
                }
                double earliest = Double.parseDouble(parts[2]);
                double latest = Double.parseDouble(parts[3]);
                // Giả sử bạn có một phương thức để tạo đối tượng Vehicle từ các thông tin này
                Vehicle vehicle = new Vehicle(capacity, park, earliest, latest);
                vehicles[i] = vehicle;
            }

            // Đọc thông tin về orders
            orders = new Order[oNum];
            for (int i = 0; i < oNum; i++) {
                String line = bufferedReader.readLine();
                parts = line.split(" ");
                double demand = Double.parseDouble(parts[0]);
                int pickupId = Integer.parseInt(parts[1]);
                Position pickup = null;
                for (int j = 0; j < Position.getPositions().length; j++) {
                    if(Position.getPositions()[j].getId() == pickupId){
                        pickup = Position.getPositions()[j];
                        break;
                    }
                }

                double pickupEarliest = Double.parseDouble(parts[2]);
                double pickupLatest = Double.parseDouble(parts[3]);
                double pickupWaitingTime = Double.parseDouble(parts[4]);
                double pickupServiceTime = Double.parseDouble(parts[5]);

                int deliveryId = Integer.parseInt(parts[6]);
                Position delivery = null;
                for (int j = 0; j < Position.getPositions().length; j++) {
                    if(Position.getPositions()[j].getId() == deliveryId){
                        delivery = Position.getPositions()[j];
                        break;
                    }
                }

                double deliveryEarliest = Double.parseDouble(parts[7]);
                double deliveryLatest = Double.parseDouble(parts[8]);
                double deliveryWaitingTime = Double.parseDouble(parts[9]);
                double deliveryServiceTime = Double.parseDouble(parts[10]);
                // Giả sử bạn có một phương thức để tạo đối tượng Order từ các thông tin này
                ActionPoint pickupPoint = new ActionPoint("pickup", pickup, pickupEarliest, pickupLatest, pickupServiceTime, pickupWaitingTime);
                ActionPoint deliveryPoint = new ActionPoint("delivery", delivery, deliveryEarliest, deliveryLatest, deliveryServiceTime, deliveryWaitingTime);
                Order order = new Order(demand, pickupPoint, deliveryPoint);
                orders[i] = order;
            }

//            // Bây giờ bạn có thể làm việc với danh sách vehicles và orders
//            System.out.println("Đã đọc dữ liệu từ tệp tin \"" + fileName + "\" thành công.");
//            // Hiển thị thông tin vehicles và orders để kiểm tra
//            for (Vehicle vehicle : vehicles) {
//                System.out.println(vehicle);
//            }
//            for (Order order : orders) {
//                System.out.println(order);
//            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ tệp tin \"" + fileName + "\": " + e.getMessage());
        }
        if (vehicles != null && orders != null) {
            return new AlgorithmInput(vehicles, orders, distanceTimeMatrix);
        }
        return null;
    }
}
