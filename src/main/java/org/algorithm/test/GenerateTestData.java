package org.algorithm.test;

import org.algorithm.*;
import org.algorithm.distancetimematrix.DistanceTimeOsmMatrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateTestData {
    static double[] capacities = new double[]{2000, 5000, 7000, 10000};
    static Position getRandPosition(){
        int len = Position.getPositions().length;
        return Position.getPositions()[Math.round((float)(Math.random())*len)%len];
    }
    static Position getRandPark(){
        int len = Position.getParks().length;
        return Position.getParks()[Math.round((float)(Math.random())*len)%len];
    }
    static Position getOtherRandPosition(Position p){
        Position temp = getRandPosition();
        int len = Position.getPositions().length;
        if(temp.getId() == p.getId()){
            temp = Position.getPositions()[(int)((temp.getId() + 20) % len)];
        }
        return temp;
    }
    static double getRandCapacity(){
        return capacities[Math.round((float)Math.random()*4)%4];
    }
    static double getRandDemand(){
        return Math.random()*1000;
    }
    static double[] getRandTimeWindow(){
        double firstTime = Math.random()*10 + 5;
        double secondTime = firstTime + Math.random()*0.5 +0.5 ;
        double firstTime1 = secondTime + Math.random()*2 + 3;
        double secondTime1 = firstTime1 + Math.random()*0.5 + 0.5;
        return new double[]{firstTime, secondTime, firstTime1, secondTime1};
    }
    public static Vehicle[] generateVehicle(int vNum){
        long hourTime = 60*60;
        Vehicle[] vehicles = new Vehicle[vNum];
        for(int i = 0; i < vNum; i++){
            vehicles[i] = new Vehicle(getRandCapacity(), getRandPark(), hourTime*4, hourTime*22);
        }
        return vehicles;
    }
    public static Order[] generateOrder(int oNum){
        long hourTime = 60*60;
        Order[] orders = new Order[oNum];
        for(int i = 0; i < oNum; i++){
            double[] rangeTime = getRandTimeWindow();
            Position pickupPosition = getRandPosition();
            ActionPoint pickupPoint = new ActionPoint("pickup", pickupPosition, Math.round(rangeTime[0]*hourTime), Math.round(rangeTime[1]*hourTime), 5*60, 5*60);
            Position deliveryPosition = getOtherRandPosition(pickupPosition);
            ActionPoint deliveryPoint = new ActionPoint("delivery", deliveryPosition, Math.round(rangeTime[2]*hourTime), Math.round(rangeTime[3]*hourTime), 5*60, 5*60);
            orders[i] = new Order(getRandDemand(), pickupPoint, deliveryPoint);
        }
        return orders;
    }
    public static AlgorithmInput generate(int vNum, int oNum){
        return new AlgorithmInput(generateVehicle(vNum), generateOrder(oNum), new DistanceTimeOsmMatrix());
    }

    public static void main(String[] args) {
//        AlgorithmInput algorithmInput = generate(10, 10);
//        algorithmInput.print();
        createSampleTest(20, 100, 10);

    }

    public static void createSampleTest(int vNum, int oNum, int testNum){
        createFolder(vNum + "_" + oNum);
        for (int i = 1; i <= testNum; i++) {
            AlgorithmInput algorithmInput = generate(vNum, oNum);
            String fileName = "dataset"+File.separator + vNum + "_" + oNum + File.separator + vNum + "_" + oNum + "_" + i + ".txt";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // Ghi dữ liệu vào tệp tin
                bufferedWriter.write(vNum+" "+oNum+"\n");
                Vehicle[] vehicles = algorithmInput.getVehicles();
                for (Vehicle vehicle : vehicles) {
                    bufferedWriter.write(vehicle.getCapacity() + " " + vehicle.getPark().getId() + " " + vehicle.getEarliest() + " " + vehicle.getLatest() + "\n");
                }
                Order[] orders = algorithmInput.getOrders();
                for (Order order : orders) {
                    bufferedWriter.write(order.getDemand() + " " + order.getPickup().getPosition().getId() + " " + order.getPickup().getEarliest() + " " + order.getPickup().getLatest() + " " + order.getPickup().getWaitingTime() + " " + order.getPickup().getServiceTime() + " " + order.getDelivery().getPosition().getId() + " " + order.getDelivery().getEarliest() + " " + order.getDelivery().getLatest() + " " + order.getDelivery().getWaitingTime() + " " + order.getDelivery().getServiceTime() + "\n");
                }
                bufferedWriter.close();

                System.out.println("Dữ liệu đã được ghi vào tệp tin \"" + fileName + "\" thành công.");
            } catch (IOException e) {
                System.err.println("Lỗi khi ghi dữ liệu vào tệp tin \"" + fileName + "\": " + e.getMessage());
            }
        }
    }

    public static void createFolder(String folderName){
        String directoryPath = "dataset/"+folderName;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean success = directory.mkdir();
            if (success) {
                System.out.println("Thư mục \"" + directoryPath + "\" đã được tạo thành công.");
            } else {
                System.out.println("Không thể tạo thư mục \"" + directoryPath + "\".");
            }
        } else {
            System.out.println("Thư mục \"" + directoryPath + "\" đã tồn tại.");
        }
    }
}