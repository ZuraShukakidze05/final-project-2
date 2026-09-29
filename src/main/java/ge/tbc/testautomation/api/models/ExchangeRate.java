package ge.tbc.testautomation.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {
    private String iso1,
            updateDate,
            iso2;
    private Double buyRate,
            currencyWeight,
            sellRate;
    private Integer conversionType;

}