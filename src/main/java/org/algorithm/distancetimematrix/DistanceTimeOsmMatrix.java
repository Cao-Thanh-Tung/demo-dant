package org.algorithm.distancetimematrix;

import org.algorithm.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DistanceTimeOsmMatrix implements IDistanceTimeMatrix {
    private static boolean isLoaded = false;
    private static int pointLen = Position.getPositions().length + Position.getParks().length;
    private static double[][] distanceMatrix = new double[pointLen][pointLen];
    private static double[][] timeMatrix = new double[pointLen][pointLen];
    public static void load() {
        String filePath = "output.txt"; // Đường dẫn đến file của bạn
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] subString = line.split(",");
                int i = Integer.parseInt(subString[0]);
                int j = Integer.parseInt(subString[1]);
                double distance = Double.parseDouble(subString[2]);
                distanceMatrix[i][j] = distance;
                double time = Double.parseDouble(subString[3]);
                timeMatrix[i][j] = time;
            }
            isLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public DistanceTimeOsmMatrix() {
        if (!DistanceTimeOsmMatrix.isLoaded)
            DistanceTimeOsmMatrix.load();
    }

    @Override
    public double getDistance(Position from, Position to) {
        return DistanceTimeOsmMatrix.distanceMatrix[(int)from.getId()][(int)to.getId()];
    }

    @Override
    public double getDuration(Position from, Position to) {
        return DistanceTimeOsmMatrix.timeMatrix[(int)from.getId()][(int)to.getId()];
    }
}
