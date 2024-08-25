package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.constant.SqlQuery;
import carshop.service.annotation.Loggable;
import carshop.service.constant.LogAction;
import carshop.service.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных car
 */

@Repository
public class CarDaoImpl implements CarDao {

    private final DataBaseConfiguration database;

    @Autowired
    public CarDaoImpl(DataBaseConfiguration database) {
        this.database = database;
    }

    @Override
    public List<Car> getAllCars() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new LinkedList<>();
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.GET_CARS_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                cars.add(
                        Car.builder()
                        .id(resultSet.getInt("id"))
                        .brand(resultSet.getString("brand"))
                        .model(resultSet.getString("model"))
                        .condition(resultSet.getString("condition"))
                        .yearOfRelease(resultSet.getInt("year_of_release"))
                        .price(resultSet.getDouble("price"))
                        .build()
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return cars;
    }

    @Override
    @Loggable(action = LogAction.ADD_CAR_ACTION)
    public void addCar(Car car) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.ADD_CAR_QUERY);
            statement.setString(1,car.getBrand());
            statement.setString(2,car.getModel());
            statement.setDouble(3,car.getPrice());
            statement.setInt(4,car.getYearOfRelease());
            statement.setString(5,car.getCondition());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }

    @Override
    public Car getCar(int id) {
        Car car = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.GET_CAR_QUERY);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car =
                        Car.builder()
                        .id(resultSet.getInt("id"))
                        .brand(resultSet.getString("brand"))
                        .model(resultSet.getString("model"))
                        .condition(resultSet.getString("condition"))
                        .yearOfRelease(resultSet.getInt("year_of_release"))
                        .price(resultSet.getDouble("price"))
                        .build();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return car;
    }

    @Override
    @Loggable(action = LogAction.DELETE_CAR_ACTION)
    public void deleteCar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.DELETE_CAR_QUERY);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    @Loggable(action = LogAction.UPDATE_CAR_ACTION)
    public void updateCar(Car car) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_CAR_QUERY);
            preparedStatement.setString(1,car.getBrand());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setDouble(3,car.getPrice());
            preparedStatement.setInt(4,car.getYearOfRelease());
            preparedStatement.setString(5,car.getCondition());
            preparedStatement.setInt(6,car.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
