package lk.ijse.project_management_tool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.controller.component.AddStaffController;
import lk.ijse.project_management_tool.controller.component.StaffCardController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    public FlowPane fpnStaffCardContainer;
    private EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
    }

    private void loadProjects() {
        try{
            fpnStaffCardContainer.getChildren().clear();
            ArrayList<EmployeeDto> employees = employeeModel.getAllEmployees();
            for (EmployeeDto employee : employees) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/StaffCard.fxml"));
                VBox projectCard  = loader.load();
                loader.<StaffCardController>getController().init(employee);
                fpnStaffCardContainer.getChildren().add(projectCard);
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddStaddOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/AddStaff.fxml"));
            Parent customContent = loader.load();
            AddStaffController controller = loader.<AddStaffController>getController();
            controller.init(this);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Canal",
                    () -> controller.saveStaff(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }
}
