package use_case.clear_users;

public class ClearInteractor implements ClearInputBoundary {
    private final ClearUserDataAccessInterface userDataAccess;
    private final ClearOutputBoundary clearPresenter;

    public ClearInteractor(ClearUserDataAccessInterface userDataAccess, ClearOutputBoundary clearPresenter) {
        this.userDataAccess = userDataAccess;
        this.clearPresenter = clearPresenter;
    }

    @Override
    public void execute(ClearInputData inputData) {
        try {
            String[] deletedUsers = userDataAccess.deleteUsers();
            ClearOutputData outputData = new ClearOutputData(deletedUsers, true);
            clearPresenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            clearPresenter.prepareFailView("Error: " + e.getMessage());
        }
    }
}