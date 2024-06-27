package org.algorithm;

public class Position {
    static long _id = 0;

    private long id;
    private double lat;
    private double lon;
    private String name;
    private static Position[] parks = new Position[]{
            new Position(21.102524651997214, 105.87117556685692, "Bãi đỗ xe Hạnh Bình"),
            new Position(21.12070753732981, 105.76425486685757, "Bãi đỗ xe Gốc Gạo"),
            new Position(21.16505788657452, 105.78006441351965, "Bãi đỗ xe Nam Hồng"),
            new Position(21.136872587557615, 105.86113086685795, "Bãi đỗ xe Hoàng Việt"),
            new Position(21.166623995542633, 105.79871153802293, "Bãi đỗ xe Thủy Bình"),
            new Position(21.158473230576615, 105.79994016685859, "Bãi đậu xe Bảo Yến"),
            new Position(21.160656230469193, 105.85644378228199, "Bãi đỗ xe Cường Phát"),
            new Position(21.123297814298503, 105.7749560195311, "Bãi đỗ xe Kim Chung"),
            new Position(21.122977560065262, 105.82885769045282, "Bãi Đỗ Xe Công Ty Hanoitrans"),
            new Position(21.1087514, 105.96020904141267, "Bãi đỗ xe Đền Đô")
    };
    private static Position[] positions = new Position[]{
            new Position(21.143180161730776, 105.84523997889337, "Viettel Post Đông Anh"),
            new Position(21.0984755613013, 105.8879674109598, "Viettel Post Thiết Ứng - Đông Anh"),
            new Position(21.056451738482252, 105.89518488259264, "Viettel Post Bưu Cục Việt Hưng"),
            new Position(21.058337677344706, 105.89056570074763, "Viettel Post Long Biên"),
            new Position(21.058068258970515, 105.83455812087676, "Viettel Post Nghi Tàm"),
            new Position(21.02762084211705, 105.84495128002806, "Viettel Post Bưu cục Cửa Đông - HNI"),
            new Position(21.08509714306714, 105.77716637281142, "Viettel Post Thụy Phương"),
            new Position(21.073668317194798, 105.80043843830519, "Viettel Post Nguyễn Hoàng Tôn"),
            new Position(21.073668317194798, 105.80043843830519, "Viettel Post Xuân La"),
            new Position(21.05614241135143, 105.80921649809673, "Viettel Post Lạc Long Quân"),
            new Position(21.041662932193525, 105.81881112158979, "Viettel Post Hoàng Hoa Thám"),

            new Position(21.041662932193525, 105.81370759845517, "Viettel Post phố Đốc Ngữ"),
            new Position(21.034803740120687, 105.81268689382826, "Viettel Post Phân Kế Bính"),
            new Position(21.03232673203638, 105.78655685537908, "Viettel Post Bưu cục Dịch Vọng Hậu 1 - HNI"),
            new Position(21.02051274245261, 105.79186451943907, "Viettel Post Trung Kính"),
            new Position(21.014986522726808, 105.80003015645443, "Viettel Post Lưu Quang Vũ"),
            new Position(21.01574877210556, 105.80860407532057, "Viettel Post Láng Hạ"),
            new Position(21.018035496866197, 105.81676971233594, "Viettel Post Chợ Dừa"),
            new Position(21.026350797677107, 105.83192725825195, "Viettel Post Bưu cục Cát Linh - HNI"),
            new Position(20.99185388005638, 105.80652175692303, "Viettel Post Hạ Đình - Thanh Xuân"),
            new Position(21.03739890390978, 105.79295291025328, "Viettel Post Bưu cục Dịch Vọng - HNI"),

            new Position(20.993135789390855, 105.73465930310617, "Viettel Post Ngã Tư La Dương"),
            new Position(21.118690852602356, 105.9653607401106, "Viettel Post P.Phủ Từ - Tân Hồng"),
            new Position(21.103317392153663, 105.95780763972668, "Viettel Post Bưu cục Từ Sơn - BNH"),
            new Position(21.08345764946549, 105.97291384049454, "Viettel Post Bưu cục Phù Chẩn - BNH"),
            new Position(21.13918632158956, 105.85275087984103, "Viettel Post CHTT Đông Anh HNI"),
            new Position(21.089864308182946, 105.92072878329644, "Viettel Post CHTT Hà Huy Tập Gia Lâm HNI"),
            new Position(21.06167294425466, 105.91454897389141, "Viettel Post Bưu cục Giang Biên - HNI"),
            new Position(21.052061030553816, 105.88570986333457, "Viettel Post Nguyễn Văn Cừ"),
            new Position(21.04629358438317, 105.86579714414056, "Viettel Post Bưu Cục Ngọc Lâm"),
            new Position(21.02258062579337, 105.89257631822905, "Viettel Post Bưu cục Bồ Đề - HNI"),

            new Position(21.045427530488887, 105.86895659919405, "Viettel Post CHTT Ngọc Lâm Long Biên HNI"),
            new Position(21.03094700945138, 105.90978478427091, "Viettel Post Nguyễn Văn Linh"),
            new Position(21.017989560927983, 105.93754795012315, "Viettel Post Bưu Cục Ngô Xuân Quảng Gia Lâm"),
            new Position(21.019895138695933, 105.96531111597541, "Viettel Post Khu đô thị Gia Lâm"),
            new Position(21.019132910577724, 105.85507501521971, "Viettel Post CHTT Phan Chu Trinh Hoàn Kiếm HNI"),
            new Position(21.025611725941037, 105.86160752483201, "Viettel Post Đường Bạch Đằng"),
            new Position(21.03247134117272, 105.8514004785628, "Viettel Post CHTT Lương Văn Can Hoàn Kiếm HNI"),
            new Position(21.285335640991807, 105.95070097697877, "Viettel post Bắc Lý"),
            new Position(21.253341893077916, 105.97198698715167, "Viettel Post Hải Tới"),
            new Position(21.207258688328125, 105.99945280672962, "Viettel Post Yên Lãng, Yên Phong"),

            new Position(21.185238329659562, 105.9551591445002, "Viettel Post Bưu cục Thị Trấn Chờ - BNH"),
            new Position(21.075849872101223, 105.94814185214614, "Viettel post Phú điền"),
            new Position(21.13337635144689, 105.99960821091675, "Viettel Post Bãi Dé, Tiên Du"),
            new Position(21.141527906169458, 106.01757288314559, "Viettel Post CHTT Tiên Du BNH"),
            new Position(21.173224132474587, 106.06175626619486, "Viettel Post Bưu cục Võ Cường - BNH"),
            new Position(21.151490308483126, 106.1370136768832, "Viettel Post Hub Quế Võ - Bắc Ninh"),
            new Position(21.185448005544416, 106.07146689983206, "Viettel Post Bưu cục Suối Hoa - BNH"),
            new Position(21.18418580006801, 106.06628358199113, "Viettel Post CHTT Tiền An BNH"),
            new Position(21.2810968676684, 106.08974669152117, "Viettel Post Thân Nhân Trung"),
            new Position(21.38982665099661, 106.1254522569725, "Viettel Post - Bưu cục Tân Yên - BGG"),
    };

    public Position(double lat, double lon, String name) {
        this.id = _id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        _id++;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public static Position[] getParks() {
        return parks;
    }

    public static Position[] getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
