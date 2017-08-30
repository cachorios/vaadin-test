package cdi.tutorial;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.vaadin.cdi.access.AccessControl;

@Alternative
public class CustomAccessControl extends AccessControl {

    @Inject
    private UserInfo userInfo;

    @Override
    public boolean isUserSignedIn() {
        return userInfo.getUser() != null;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (isUserSignedIn()) {
            for (String userRole : userInfo.getRoles()) {
                if (role.equals(userRole)) {
                   /// System.out.println("es admin");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getPrincipalName() {
        if (isUserSignedIn()) {
            return userInfo.getUser().getUsername();
        }
        return null;
    }

}