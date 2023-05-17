package grid.onlineshop.sweetland.dto.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

  private static final long serialVersionUID = 5926468583005150707L;

  private String email;
  private String password;

  public LoginRequest() {}

  public LoginRequest(String username, String password) {
    this.setEmail(username);
    this.setPassword(password);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
