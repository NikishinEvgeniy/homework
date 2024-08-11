package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Car;
import carshop_service.entity.StandartCarBuilder;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CarDaoImpl implements CarDao {

    private ConfigLoader configLoader;

    @Override
    public List<Car> getAllCars() {
        Connection connection = configLoader.getConnection();
        List<Car> cars = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getClientsQuery = "SELECT * FROM car";
            statement = connection.prepareStatement(getClientsQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                cars.add(
                        new StandartCarBuilder()
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
                connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return cars;
    }

    @Override
    public void addCar(Car car) {
        Connection connection = configLoader.getConnection();
        PreparedStatement statement = null;
        try {
            String addClientQuery = "INSERT INTO car (brand,model,price,year_of_release,condition) " +
                    "VALUES(?,?,?,?,?)";
            configLoader.setSchema(connection.createStatement());
            statement = connection.prepareStatement(addClientQuery);
            statement.setString(1,car.getBrand());
            statement.setString(2,car.getModel());
            statement.setDouble(3,car.getPrice());
            statement.setInt(4,car.getYearOfRelease());
            statement.setString(5,car.getCondition());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
                if(statement != null) statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }

    @Override
    public Car getCar(int id) {
        Connection connection = configLoader.getConnection();
        Car car = null;
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getClientQuery = "SELECT * FROM car WHERE id=?";
            statement = connection.prepareStatement(getClientQuery);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            car = new StandartCarBuilder()
                    .id(resultSet.getInt("id"))
                    .brand(resultSet.getString("brand"))
                    .model(resultSet.getString("model"))
                    .condition(resultSet.getString("condition"))
                    .yearOfRelease(resultSet.getInt("year_of_release"))
                    .price(resultSet.getDouble("price"))
                    .build();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(statement != null) statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }

        return car;
    }

    @Override
    public void deleteCar(int id) {
        Connection connection = configLoader.getConnection();
        String deleteCarQuery = "DELETE FROM car WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(deleteCarQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void updateCar(Car car) {
        Connection connection = configLoader.getConnection();
        String updateCarQuery = "UPDATE car " +
                "SET brand = ?, model = ?," +
                "price = ?, year_of_release = ?," +
                "condition = ? " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(updateCarQuery);
            preparedStatement.setString(1,car.getBrand());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setDouble(3,car.getPrice());
            preparedStatement.setInt(4,car.getYearOfRelease());
            preparedStatement.setString(5,car.getCondition());
            preparedStatement.setInt(6,car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
