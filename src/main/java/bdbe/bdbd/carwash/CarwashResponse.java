package bdbe.bdbd.carwash;

import bdbe.bdbd.location.Location;
import bdbe.bdbd.optime.Optime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

public class CarwashResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private Long id;
        private String name;
        private double rate;
        private String tel;
        private String des;
        private int price;
        private Long lId;
        private Long userId;

        public FindAllDTO(Carwash carwash) {
            this.id = carwash.getId();
            this.name = carwash.getName();
            this.rate = carwash.getRate();
            this.tel = carwash.getTel();
            this.des = carwash.getDes();
            this.price = carwash.getPrice();
            this.lId = carwash.getLocation().getId();
            this.userId = carwash.getUser().getId();
        }
    }

    @Getter
    @Setter
    public class KeywordResponseDTO {
        private String keywordName;

        public KeywordResponseDTO(String keywordName) {
            this.keywordName = keywordName;
        }

        // getters, setters
    }



    @Getter
    @Setter
    public static class findByIdDTO {
        private Long id;
        private String name;
        private double rate;
        private int reviewCnt; // 리뷰 갯수
        private int bayCnt; // 예약 가능한 갯수
        private OperatingTimeDTOResponse optime; //
        private CarwashResponse.locationDTO locationDTO;
        private List<Long> keywordId;
        private String description;
        private String tel;
//        private List<String> image;


        public findByIdDTO(Carwash carwash, int reviewCnt, int bayCnt, Location location, List<Long> keywordId, Optime weekOptime, Optime endOptime) {
//            this.image = image;
            this.id = carwash.getId();
            this.name = carwash.getName();
            this.rate = carwash.getRate();
            this.reviewCnt = reviewCnt;
            this.bayCnt = bayCnt;
            this.optime = toOptimeListDTO(weekOptime, endOptime);
            this.locationDTO = toLocationDTO(location);
            this.keywordId = keywordId;
            this.description = carwash.getDes();
            this.tel = carwash.getTel();
        }

        public OperatingTimeDTOResponse toOptimeListDTO(Optime weekOptime, Optime endOptime) {
            OperatingTimeDTOResponse dto = new OperatingTimeDTOResponse();
            OperatingTimeDTOResponse.TimeSlotResponse weekSlot= new OperatingTimeDTOResponse.TimeSlotResponse();
            weekSlot.setStart(weekOptime.getStartTime());
            weekSlot.setEnd(weekOptime.getEndTime());
            dto.setWeekday(weekSlot);

            OperatingTimeDTOResponse.TimeSlotResponse endSlot= new OperatingTimeDTOResponse.TimeSlotResponse();
            weekSlot.setStart(endOptime.getStartTime());
            weekSlot.setEnd(endOptime.getEndTime());
            dto.setWeekday(weekSlot);

            return dto;

        }

        public locationDTO toLocationDTO(Location location) {
            locationDTO locationDTO = new locationDTO();
            locationDTO.setAddress(location.getAddress());
            locationDTO.setPlaceName(location.getPlace());
            return locationDTO;
        }
    }
    @Getter
    @Setter
    public static class OperatingTimeDTOResponse {
        private TimeSlotResponse weekday;
        private TimeSlotResponse weekend;

        @Getter
        @Setter
        public static class TimeSlotResponse {
            private LocalTime start;
            private LocalTime end;

        }
    }

    @Getter
    @Setter
    public static class locationDTO {
        private String placeName;
        private String address;
        private double latitude;
        private double longitude;
    }

    @Getter
    @Setter
    public static class carwashDetailsDTO {
        private Long id;
        private String name;
        private int price;
        private String tel;
        private detailLocationDTO locationDTO;
        private detailsOperatingTimeDTO optime; //
        private List<Long> keywordId;
        private String description;
//        private List<String> image;


        public carwashDetailsDTO(Carwash carwash, Location location, List<Long> keywordId, Optime weekOptime, Optime endOptime) {
//            this.image = image;
            this.id = carwash.getId();
            this.name = carwash.getName();
            this.price = carwash.getPrice();
            this.tel = carwash.getTel();
            this.locationDTO = toLocationDTO(location);
            this.optime = toOptimeListDTO(weekOptime, endOptime);
            this.keywordId = keywordId;
            this.description = carwash.getDes();
        }

        public detailsOperatingTimeDTO toOptimeListDTO(Optime weekOptime, Optime endOptime) {
            detailsOperatingTimeDTO dto = new detailsOperatingTimeDTO();
            detailsOperatingTimeDTO.detailsTimeSlot weekSlot= new detailsOperatingTimeDTO.detailsTimeSlot();
            weekSlot.setStart(weekOptime.getStartTime());
            weekSlot.setEnd(weekOptime.getEndTime());
            dto.setWeekday(weekSlot);

            detailsOperatingTimeDTO.detailsTimeSlot endSlot= new detailsOperatingTimeDTO.detailsTimeSlot();
            endSlot.setStart(endOptime.getStartTime());
            endSlot.setEnd(endOptime.getEndTime());
            dto.setWeekend(endSlot);

            return dto;

        }

        public detailLocationDTO toLocationDTO(Location location) {
            detailLocationDTO detailLocationDTO = new detailLocationDTO();
            detailLocationDTO.setAddress(location.getAddress());
            return detailLocationDTO;
        }
    }
    @Getter
    @Setter
    public static class detailsOperatingTimeDTO {
        private detailsTimeSlot weekday;
        private detailsTimeSlot weekend;

        @Getter
        @Setter
        public static class detailsTimeSlot {
            private LocalTime start;
            private LocalTime end;

        }
    }

    @Getter
    @Setter
    public static class detailLocationDTO {
        private String address;

    }

    @Getter
    @Setter
    @ToString
    public static class updateCarwashDetailsResponseDTO {
        private Long id;
        private String name;
        private int price;
        private String tel;
        private CarwashResponse.updateLocationDTO locationDTO;
        private CarwashResponse.updateOperatingTimeDTO optime;
        private List<Long> keywordId;
        private String description;

        //        private List<String> images;
        public void updateCarwashPart(Carwash carwash) {
            this.id = carwash.getId();
            this.name = carwash.getName();
            this.price = carwash.getPrice();
            this.tel = carwash.getTel();
        }

        public void updateOptimePart(Optime weekdayOptime, Optime weekendOptime) {
            CarwashResponse.updateOperatingTimeDTO dto = new CarwashResponse.updateOperatingTimeDTO();

            CarwashResponse.updateOperatingTimeDTO.updateTimeSlot weekSlot = new CarwashResponse.updateOperatingTimeDTO.updateTimeSlot();
            weekSlot.setStart(weekdayOptime.getStartTime());
            weekSlot.setEnd(weekdayOptime.getEndTime());
            dto.setWeekday(weekSlot);

            CarwashResponse.updateOperatingTimeDTO.updateTimeSlot endSlot = new CarwashResponse.updateOperatingTimeDTO.updateTimeSlot();
            endSlot.setStart(weekendOptime.getStartTime());
            endSlot.setEnd(weekendOptime.getEndTime());
            dto.setWeekend(endSlot);
            this.optime = dto;

        }

        public void updateKeywordPart(List<Long> keywordId) {
            this.keywordId = keywordId;
        }

        public void updateLocationPart(Location location) {
            CarwashResponse.updateLocationDTO dto = new CarwashResponse.updateLocationDTO();
            dto.setPlaceName(location.getPlace());
            dto.setAddress(location.getAddress());
            dto.setLatitude(location.getLatitude());
            dto.setLongitude(location.getLongitude());
            this.locationDTO = dto;
        }
    }
    @Getter
    @Setter
    public static class updateOperatingTimeDTO {
        private CarwashResponse.updateOperatingTimeDTO.updateTimeSlot weekday;
        private CarwashResponse.updateOperatingTimeDTO.updateTimeSlot weekend;

        @Getter
        @Setter
        public static class updateTimeSlot {
            private LocalTime start;
            private LocalTime end;

        }
    }

    @Getter
    @Setter
    public static class updateLocationDTO {
        private String placeName;
        private String address;
        private double latitude;
        private double longitude;

    }

}
