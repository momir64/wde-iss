package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;
import wedoevents.eventplanner.userManagement.repositories.userTypes.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Optional<Admin> getAdminById(UUID id) {
        return adminRepository.findById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void deleteAdmin(UUID id) {
        adminRepository.deleteById(id);
    }
}