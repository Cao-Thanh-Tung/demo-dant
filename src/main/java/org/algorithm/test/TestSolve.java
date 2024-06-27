package org.algorithm.test;

import org.algorithm.*;
import org.algorithm.constraint.CapacityConstraint;
import org.algorithm.constraint.IConstraint;
import org.algorithm.constraint.TimeWindowConstraint;
import org.algorithm.distancetimematrix.DistanceTimeOsmMatrix;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;
import org.algorithm.objective.MinDistanceObjective;
import org.algorithm.objective.MinDurationObjective;
import org.algorithm.objective.IObjective;
import org.algorithm.objective.MaxServeAbleOrderObjective;
import org.algorithm.strategy.IStrategy;
import org.algorithm.strategy.greedy.FirstFitOrderAssignStrategy;
import org.algorithm.strategy.greedy.MinDistanceFitOrderAssignStrategy;
import org.algorithm.strategy.greedy.RoundRobinOrderAssignStrategy;
import org.algorithm.strategy.heuristic.HeuristicStrategy;
import org.algorithm.strategy.heuristic.ShuffleFirstFitStrategy;
import org.algorithm.strategy.heuristic.ShuffleMinDistanceFitStrategy;
import org.algorithm.strategy.heuristic.ShuffleRoundRobinStrategy;

import java.io.*;

public class TestSolve {
    public static void main(String[] args) {

        String csvFile = "result500.csv";
        String[] header = {"file_data", "strategy", "run_order", "vehicle_num", "order_num", "total_distance", "total_duration", "total_serve_able_order", "total_demand", "total_vehicle_used", "run_time"};
        String[] folderNames = {"5_20", "10_50", "10_100", "20_100", "50_1000"};
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
        IStrategy[] strategies = {
//                new FirstFitOrderAssignStrategy(),
//                new MinDistanceFitOrderAssignStrategy(),
//                new RoundRobinOrderAssignStrategy(),
                new ShuffleRoundRobinStrategy(),
//                new ShuffleFirstFitStrategy(),
//                new ShuffleMinDistanceFitStrategy(),
        };
        AlgorithmConfig config = new AlgorithmConfig();
        config.setConstraints(constraints);
        config.setObjectives(objectives);
//        int[] nums = {500};
        int n = 500;
//        AlgorithmInput input = getInputFromFile("dataset" + File.separator + "10_100" + File.separator + "10_100_1.txt", distanceTimeMatrix);
//        Solution solution = (new FirstFitOrderAssignStrategy()).createSolution(input, config);
//        System.out.println(solution.totalDistance);
//        Solution solution1 = (new MinDistanceFitOrderAssignStrategy()).createSolution(input, config);
//        System.out.println(solution1.totalDistance);
//        solution.printSolution();
//        solution1.printSolution();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writeLine(writer, header);
            for (String fo : folderNames) {
                for (IStrategy strategy : strategies){
                    if (strategy instanceof HeuristicStrategy){
                        for (int i = 1; i <= 10; i++) {
                            String fileName =fo + "_" + i + ".txt";
                                AlgorithmInput input = getInputFromFile("dataset" + File.separator + fo + File.separator + fileName, distanceTimeMatrix);
                                config.setNumShuffle(n);
                                System.out.println("Đang xử lý file: " + fileName + " với chiến lược: " + strategy.getName() + " số lượng xe: " + input.getVehicles().length + " số lượng đơn hàng: " + input.getOrders().length + "numShuffer: "+ n);
                                long startTime = System.currentTimeMillis();
                                Solution solution = strategy.createSolution(input, config);
                                long endTime = System.currentTimeMillis();
                                long duration = endTime - startTime;
                                writer.write(fileName +","+strategy.getName()+","+1+","+input.getVehicles().length+","+input.getOrders().length+","+solution.totalDistance+","+solution.totalDuration+","+solution.totalServeAbleOrder+","+solution.totalDemand+","+solution.numVehicleUsed+","+duration);
                                writer.newLine();
                        }
                    }else{
                        for (int i = 1; i <= 10; i++) {
                            String fileName =fo + "_" + i + ".txt";
                            for (int j = 1; j <= 20; j++) {
                                AlgorithmInput input = getInputFromFile("dataset" + File.separator + fo + File.separator + fileName, distanceTimeMatrix);
                                int vN = input.getVehicles().length;
                                int oN = input.getOrders().length;
                                System.out.println("Đang xử lý file: " + fileName + " với chiến lược: " + strategy.getName() + " lần chạy thứ: " + j + " số lượng xe: " + vN + " số lượng đơn hàng: " + oN);
                                long startTime = System.currentTimeMillis();
                                    Solution solution = strategy.createSolution(input, config);
                                    long endTime = System.currentTimeMillis();
                                    long duration = endTime - startTime;
                                    writer.write(fileName +","+strategy.getName()+","+j+","+vN+","+oN+","+solution.totalDistance+","+solution.totalDuration+","+solution.totalServeAbleOrder+","+solution.totalDemand+","+solution.numVehicleUsed+","+duration);
                                    writer.newLine();
                            }
                        }
                    }
                }
            }
            System.out.println("Đã tạo file CSV thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }


//        IDistanceTimeMatrix distanceTimeMatrix = new DistanceTimeOsmMatrix();
//        AlgorithmInput input = getInputFromFile("dataset" + File.separator + "5_20" + File.separator + "5_20_1.txt", distanceTimeMatrix);
//        AlgorithmConfig config = new AlgorithmConfig();
//        IConstraint[] constraints = {
//                new TimeWindowConstraint(distanceTimeMatrix),
//                new CapacityConstraint()
//        };
//        config.setConstraints(constraints);
//        IStrategy strategy = new ChooseOrderFirstStrategy();
//        Solution solution = strategy.createSolution(input, config);

//        solution.printSolution();
    }

    private static void writeLine(BufferedWriter writer, String[] values) throws IOException {
        writer.write(String.join(",", values));
        writer.newLine();
    }

    public static void printResultToFile(){
        try {
            FileWriter fileWriter = new FileWriter("result.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // Ghi dữ liệu vào tệp tin
            bufferedWriter.write("5 20\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi dữ liệu vào tệp tin \"" + "\": " + e.getMessage());
        }
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
