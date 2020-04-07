package karol.fitnotes.service;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.domain.Token;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.AppUserRepo;
import karol.fitnotes.repository.TokenRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private TokenRepo tokenRepo;
    private MailService mailService;

    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(appUser);
        tokenRepo.save(token);

        String url = "https://training-notes.herokuapp.com/token?value=" + tokenValue;
        try {
            mailService.sendMail(appUser.getUsername(), "Potwierdz", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public AppUser findByUsername(String username) {
        return appUserRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
    }

    public AppUser getById(Long id) {
        return appUserRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public List<Training> getAllTrainings(String username) {
        List<Training> trainings = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username))
                .getTrainings();

        trainings.sort(Comparator.comparing(Training::getId).reversed());
        return trainings;
    }

    public void confirmToken(String value) {
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnable(true);
        appUserRepo.save(appUser);
    }
}
