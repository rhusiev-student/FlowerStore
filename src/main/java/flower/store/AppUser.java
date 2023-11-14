package flower.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Users")
public class AppUser {
    @Id private int id;
    @Column(unique = true) private String email;
    @Transient private LocalDate dob;
    private int age;
    private String status;

    public Period getDob() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        return period;
    }

    public void update(String status) {
        this.status = status;
    }
}
