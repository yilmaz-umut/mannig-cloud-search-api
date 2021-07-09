package com.example.demo;

import com.example.demo.integration.meaningcloud.MeaningCloudIntegrationService;
import com.example.demo.pubmedquery.SearchHandler;
import com.example.demo.repository.PubmedArticlesRepository;
import com.example.demo.security.repository.RoleRepository;
import com.example.demo.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  private final SearchHandler searchHandler;

  private final UserRepository userRepository;

  private final MeaningCloudIntegrationService meaningCloudIntegrationService;

  private final PubmedArticlesRepository pubmedArticlesRepository;

  public DemoApplication(RoleRepository roleRepository,
                         PasswordEncoder encoder, SearchHandler searchHandler,
                         UserRepository userRepository, MeaningCloudIntegrationService meaningCloudIntegrationService, PubmedArticlesRepository pubmedArticlesRepository) {
    this.roleRepository = roleRepository;
    this.encoder = encoder;
    this.searchHandler = searchHandler;
    this.userRepository = userRepository;
    this.meaningCloudIntegrationService = meaningCloudIntegrationService;
    this.pubmedArticlesRepository = pubmedArticlesRepository;
  }


  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {




    /*
    Role role = new Role();
    role.setName("R_USER");
    roleRepository.save(role);


    User user = new User();
    user.setUsername("boun.admin");
    user.setPassword(encoder.encode("boun.admin"));
    user.setEmail("boun.admin@gmail.com");

    Set<Role> roles = new HashSet<>();
    roles.add(roleRepository.findByName("R_USER").orElse(null));
    user.setRoles(roles);

    userRepository.save(user);*/


    searchHandler.executeESearchQuery();
    pubmedArticlesRepository.findAll().forEach(pubmedArticles -> {
      System.out.println(pubmedArticles.toString());
    });


  }
}
