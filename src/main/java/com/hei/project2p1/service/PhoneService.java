package com.hei.project2p1.service;

import com.hei.project2p1.model.Company;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.PhoneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hei.project2p1.utils.PaginationUtils.paginationValidator;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@AllArgsConstructor
public class PhoneService {
    private final PhoneRepository repository;
    public List<Phone> getAll(int page, int pageSize){
        paginationValidator(page,pageSize);
        Pageable pageable = PageRequest.of(page - 1,pageSize,
                Sort.by(ASC,"employee"));
        return repository.findAll(pageable).toList();
    }

    public List<Phone> getByCodeAndNumber(String code, String number){
        return repository.findAllByCountryCodeAndNumber(code,number);
    }

    public List<Phone> getByOwnerId(String ownerId){
        return repository.getPhoneByOwnerId(ownerId);
    }

    public List<Phone> getByCompanyId(String ownerId){
        return repository.findAllByCompanyId(ownerId);
    }

    @Transactional
    public List<Phone> savePhones(Employee owner,List<String> countryCode, List<String> toSave) {
        return repository.saveAll(addPhonesToOwner(owner,countryCode, toSave));
    }
    @Transactional
    public List<Phone> savePhones(Company owner, List<String> countryCode, List<String> toSave) {
        return repository.saveAll(addPhonesToOwner(owner,countryCode,toSave));
    }
    @Transactional
    public void deletePhonesOfOwner(Employee owner){
        List<Phone> toDelete = getByOwnerId(owner.getId());
        repository.deleteAll(toDelete);
    }

    @Transactional
    public void deletePhonesOfOwner(Company owner){
        List<Phone> toDelete = getByCompanyId(owner.getId());
        repository.deleteAll(toDelete);
    }
    public List<Phone> addPhonesToOwner(Employee owner,List<String> countryCodes, List<String> toSave) {
        List<Phone> phoneList = new ArrayList<>();
        for (int i = 0; i < toSave.size(); i++) {
            phoneList.add(Phone.builder()
                    .employee(owner)
                    .countryCode(countryCodes.get(i))
                    .number(toSave.get(i))
                    .company(null)
                    .build());
        }
        return phoneList;
    }
    public List<Phone> addPhonesToOwner(Company owner, List<String> countryCodes, List<String> toSave) {
        List<Phone> phoneList = new ArrayList<>();
        for (int i = 0; i < toSave.size(); i++) {
            phoneList.add(Phone.builder()
                    .employee(null)
                    .countryCode(countryCodes.get(i))
                    .number(toSave.get(i))
                    .company(owner)
                    .build());
        }
        return phoneList;
    }
}
