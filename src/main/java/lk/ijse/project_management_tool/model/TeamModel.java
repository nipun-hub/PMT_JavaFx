package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamModel {
    public boolean saveTeam(TeamDto team) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO teams (name, description) VALUES (?,?)";
        return CrudUtil.execute(sql,
                team.getName(),
                team.getDescription()
        );
    }

    public boolean updateTeamStatus(int teamId,String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE teams SET status=? WHERE team_id=?";
        return CrudUtil.execute(sql,
                status,
                teamId
        );
    }

    public boolean updateTeam(TeamDto team) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE teams SET name=?, description=? WHERE team_id=?";
        return CrudUtil.execute(sql,
                team.getName(),
                team.getDescription(),
                team.getTeamId()
        );
    }


    public boolean deleteTeam(Long teamId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM teams WHERE team_id=?";
        return CrudUtil.execute(sql, teamId);
    }

    public ArrayList<TeamDto> getAllTeams() throws SQLException, ClassNotFoundException {
        String sql = "SELECT teams.*,count(employee.employee_id) AS EmployeeCount FROM teams JOIN pmt.employee ON teams.team_id = employee.team_id WHERE teams.status !='SUSPEND' GROUP BY  teams.team_id";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<TeamDto> teams = new ArrayList<>();

        while (resultSet.next()) {
            teams.add(new TeamDto(
                    resultSet.getInt("team_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getInt("EmployeeCount")
            ));
        }
        return teams;
    }

    public ArrayList<TeamDto> getAllTeamsWithSuspend() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM teams";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<TeamDto> teams = new ArrayList<>();

        while (resultSet.next()) {
            teams.add(new TeamDto(
                    resultSet.getInt("team_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("status")
            ));
        }
        return teams;
    }

    public TeamDto getTeamById(Long teamId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM teams WHERE team_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, teamId);

        if (resultSet.next()) {
            return new TeamDto(
                    resultSet.getInt("team_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("status")
            );
        }
        return null;
    }
} 