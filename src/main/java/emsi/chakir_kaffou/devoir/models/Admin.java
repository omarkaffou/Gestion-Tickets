package emsi.chakir_kaffou.devoir.models;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import emsi.chakir_kaffou.devoir.utils.Constants;

@Entity
@AttributeOverride(name = "user_id", column = @Column(name = "admin_id"))
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "user_id")
public class Admin extends User {

    public Admin() {
        super();
        this.setRole(Constants.getRole("admin"));
    }

    public Admin(String email, String displayName, String password) {
        super(email, displayName, password);
        this.setRole(Constants.getRole("admin"));
    }
}
