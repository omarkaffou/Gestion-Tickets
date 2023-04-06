package emsi.chakir_kaffou.devoir.models;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import emsi.chakir_kaffou.devoir.utils.Constants;

@Entity
@AttributeOverride(name = "user_id", column = @Column(name = "client_id"))
@PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "user_id")
public class Client extends User {

    public Client() {
        super();
        this.setRole(Constants.getRole("client"));
    }

    public Client(String email, String displayName, String password) {
        super(email, displayName, password);
        this.setRole(Constants.getRole("client"));
    }
}
