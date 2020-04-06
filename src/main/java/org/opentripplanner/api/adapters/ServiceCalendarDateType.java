package org.opentripplanner.api.adapters;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.opentripplanner.model.FeedScopedId;
import org.opentripplanner.model.calendar.ServiceCalendarDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "CalendarDate")
public class ServiceCalendarDateType {

    public ServiceCalendarDateType(FeedScopedId serviceId, long date, int exceptionType) {
        this.serviceId = serviceId;
        this.date = date;
        this.exceptionType = exceptionType;
        switch (this.exceptionType) {
        case 1:
            this.exception = "Remove";
            break;
        case 2:
            this.exception = "Add";
            break;
        default:
            this.exception = "";
        }
    }

    public ServiceCalendarDateType(ServiceCalendarDate arg) {
        this.serviceId = arg.getServiceId();
        this.date = arg.getDate().getAsDate().getTime();
        this.exceptionType = arg.getExceptionType();
        switch (this.exceptionType) {
        case 1:
            this.exception = "Remove";
            break;
        case 2:
            this.exception = "Add";
            break;
        default:
            this.exception = "";
        }
    }

    public ServiceCalendarDateType() {
    }

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    public FeedScopedId serviceId;

    @XmlAttribute
    @JsonSerialize
    public Long date;

    @XmlAttribute
    @JsonSerialize
    public Integer exceptionType;

    @XmlAttribute
    @JsonSerialize
    public String exception;

}