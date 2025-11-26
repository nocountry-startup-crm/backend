package com.nocountry.crm.mapper.helper;

import com.nocountry.crm.entity.Country;
import com.nocountry.crm.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CountryMapperHelper {

    private final CountryRepository countryRepository;

    public List<Country> mapCountryCodesToCountries(List<String> codes) {
        if (codes == null) return null;

        List<Country> countries = new ArrayList<>();
        for (String code : codes) {
            Country c = countryRepository.findByCode(code)
                    .orElseThrow(() -> new RuntimeException("País no encontrado con código " + code));
            if (c != null) {
                countries.add(c);
            }
        }
        return countries;
    }

    public List<String> mapCountriesToCodes(List<Country> countries) {
        if (countries == null) return null;

        List<String> codes = new ArrayList<>();
        for (Country c : countries) {
            if (c != null && c.getAcronym() != null) {
                codes.add(c.getAcronym());
            }
        }
        return codes;
    }
}

