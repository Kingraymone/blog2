package top.king.entity.finance;

public class Day {
    private Integer id;

    private String workDay;

    private Integer calendarDate;

    public Day(Integer id, String workDay, Integer calendarDate) {
        this.id = id;
        this.workDay = workDay;
        this.calendarDate = calendarDate;
    }

    public Day() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay == null ? null : workDay.trim();
    }

    public Integer getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Integer calendarDate) {
        this.calendarDate = calendarDate;
    }
}