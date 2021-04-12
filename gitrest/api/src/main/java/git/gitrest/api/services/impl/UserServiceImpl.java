package git.gitrest.api.services.impl;


import git.gitrest.api.entities.Roles;
import git.gitrest.api.entities.User;
import git.gitrest.api.repository.AdminRepository;
import git.gitrest.api.repository.RoleRepository;
import git.gitrest.api.repository.UserRepository;
import git.gitrest.api.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    
	private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminRepository = adminRepository;


	}

	@Override
    public void save(User user) {

        int ids = 1;
        Long id = new Long(ids);

        Optional<Roles> roles =  roleRepository.findById(id);
        List<Roles> allroles = new ArrayList<>();
        allroles.add(roles.get());
        System.out.println("password");
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(roles.get());
        user.setRoles(new HashSet<>(allroles));
        userRepository.save(user);
    }

    public void adminsave(User user) {

        int ids = 2;
        Long id = new Long(ids);

        Optional<Roles> roles =  roleRepository.findById(id);
        List<Roles> allroles = new ArrayList<>();
        allroles.add(roles.get());
        System.out.println("password");
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(roles.get());
        user.setRoles(new HashSet<>(allroles));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getProfilepics(String username){
        User user = userRepository.findByUsername(username);
        return user.getImgurl();
    }
    public void setProfilepics(String username, String picsurl){
        User user = userRepository.findByUsername(username);
        user.setImgurl(picsurl);
        userRepository.save(user);
    }
    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


	

}
