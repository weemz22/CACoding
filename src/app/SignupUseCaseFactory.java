package app;

import interface_adapter.clear_users.ClearController;
import interface_adapter.clear_users.ClearViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.SignupUserDataAccessInterface;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.*;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.SignupView;

import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {

    private SignupUseCaseFactory() {
        // Prevent instantiation
    }

    public static SignupView createSignupView(
            ViewManagerModel viewManager,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            SignupUserDataAccessInterface userDataAccess,
            ClearController clearController,
            ClearViewModel clearViewModel) {

        try {
            SignupController signupController = createSignupController(viewManager, signupViewModel, loginViewModel, userDataAccess);
            return new SignupView(signupController, signupViewModel, clearViewModel, clearController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Could not access user data file.");
            return null;
        }
    }

    /**
     * Creates a SignupController.
     * @param viewManager the view manager model
     * @param signupViewModel the signup view model
     * @param loginViewModel the login view model
     * @param userDataAccess the user data access interface
     * @return a new SignupController instance
     * @throws IOException if there is an error accessing user data
     */
    private static SignupController createSignupController(
            ViewManagerModel viewManager,
            SignupViewModel signupViewModel,
            LoginViewModel loginViewModel,
            SignupUserDataAccessInterface userDataAccess) throws IOException {

        SignupOutputBoundary presenter = new SignupPresenter(viewManager, signupViewModel, loginViewModel);
        UserFactory userFactory = new CommonUserFactory();
        SignupInputBoundary signupInteractor = new SignupInteractor(userDataAccess, presenter, userFactory);

        return new SignupController(signupInteractor);
    }
}

