import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//DAO -- data access object
public class DAOProduct {


    private Connection connection;

    public DAOProduct(final String dbFile) {//��� ���������� ������ ��������������� ������
        try {
            Class.forName("org.sqlite.JDBC");

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);//"jdbc:sqlite::memory"
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t find and load SQLite JDBC class", e);//�������� ������� �������

        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("Can`t find SQL class");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }

        }
        initTable();

    }

    private static String like(final String fieldName, final String query) {
        return query != null ? fieldName + "LIKE '%" + query + "%'" : null;

    }

    private static String in(final String field, final Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return field + " IN (" + collection.stream().map(Objects::toString).collect(Collectors.joining(", ")) + ")";

    }


    private static <T> String gte(final String field, final Double value) {
        if (value == null) return null;

        return field + " >= " + value;

    }


    private static <T> String lte(final String field, final Double value) {
        if (value == null) return null;

        return field + " <= " + value;

    }

//
//    private static String range(final String field, final Double from, final Double to) {
//
//    }


    //CREATE/INSERT to the table
    @SneakyThrows
    public int insertProduct(final Product product) {

        try (final PreparedStatement insertStatement = connection.prepareStatement("insert into products('name','price','amount') values (?,?,?)")) {
            connection.setAutoCommit(false);

//            insertStatement.setInt(1, product.getId());
            insertStatement.setString(1, product.getName());
            insertStatement.setDouble(2, product.getPrice());
            insertStatement.setInt(3, product.getAmount());

            insertStatement.execute();
            connection.commit();//��������� ����������

            final ResultSet resultSet = insertStatement.getGeneratedKeys();//�������� ������ ��� ��� ��� ��������

            return resultSet.getInt("last_insert_rowid()");//�������� ������ ������ ��������


        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new FoundOfSQLClassException("Can`t insert new product into table");//�������� ������� �������


            } catch (SQLException e1) {
                throw new FoundOfSQLClassException("Can`t rollback transaction");//�������� ������� �������

            }

        }
    }

    //UPDATE info of product
    public void updateTable(Integer id, Product product) {

        try (final PreparedStatement updateStatement = connection.prepareStatement("UPDATE products SET name = ?, price = ?, amount = ? WHERE id = ?")) {

            // set the corresponding param
            updateStatement.setString(1, product.getName());
            updateStatement.setDouble(2, product.getPrice());
            updateStatement.setInt(3, product.getAmount());
            updateStatement.setInt(4, id);

            // update
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("Can`t update product");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }
        }


    }


    //DELETE the product

    public void deleteProduct(Integer id) {

        try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {

            // set the corresponding param
            deleteStatement.setInt(1, id);

            // execute the delete statement
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("Can`t delete product");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }

        }

    }



    @SneakyThrows
    public List<Product> getList(final int page, final int size) {

        try (final Statement statement = connection.createStatement()) {


            final ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * from 'products' LIMIT %s OFFSET %s", size, page * size)
            );

            final List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("amount")));
            }
            return products;


        } catch (SQLException e) {
            throw new FoundOfSQLClassException("Can`t create product table");//�������� ������� �������
        }

    }





    //LIST BY CRITERIA
    @SneakyThrows
    public List<Product> getProductList(final int page, final int size, final ProductFilter productFilter) {//page - ����� ������(���������� ����� �������), size - ����� ������(�-�� ���� �� �������) �� ���
        try (final Statement statement = connection.createStatement()) {

//            final String query="";
//
//            if (productFilter.getIds()!=null)


            final String query1 = Stream.of(in("id", productFilter.getIds()), gte("price", productFilter.getFromPrice()), lte("price", productFilter.getToPrice())).filter(Objects::nonNull).collect(Collectors.joining(" AND "));


//            final String query = Stream.of(like("name", productFilter.getQuery()),
//                    in("id", productFilter.getIds())).filter(Objects::nonNull)
//                    .collect(Collectors.joining(" AND "));


            final String where = query1.isEmpty() ? "" : " where " + query1;
            final String finalSql = String.format("SELECT * from 'products' %s LIMIT %s OFFSET %s", where, size, page * size);//limit ����� ������ �� ������ �������� ����� �� ���, offset ������ �� ������� �����

            final ResultSet resultSet = statement.executeQuery(finalSql);//� ����� ������� �������� sql ���� ���� �������� �� �� ������� ��� �� ���������� 10 �������� �������


            final List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("amount")));
            }

            return products;


        } catch (SQLException e) {
            throw new FoundOfSQLClassException("Can`t create product table");//�������� ������� �������
        }


    }


    public void deleteAll() {
        try (final Statement statement = connection.createStatement()) {

            statement.execute("delete * from 'products'");

        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("Can`t create product table");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }
        }

    }


    //����� �� �������� �� ����� �������� ������� � ����� ������ �� ���� �� �� ������������� ������ ���� ���������
    @SneakyThrows
    public boolean isNameUnique(final String productName) {//page - ����� ������, size - ����� ������ �� ���
        try (final Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery(String.format("SELECT count(*) as number_of_products from 'products' where name = '%s'", productName));//� ����� ������� �������� sql ���� ���� �������� �� �� ������� ��� �� ���������� 10 �������� �������

            resultSet.next();

            return resultSet.getInt("number_of_products") == 0;

        } catch (SQLException e) {
            throw new FoundOfSQLClassException("Can`t create product table");//�������� ������� �������
        }


    }

    //����������� ���� ���� �����
    public void initTable() {
        try (final Statement statement = connection.createStatement()) {

            statement.execute("create table if not exists 'products' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,'name' text not null,'price' REAL not null,'amount' INTEGER not null,unique(name));");
//� ����� ������� �������� sql ���� ���� �������� �� �� ������� ��� �� ���������� 10 �������� �������

        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("Can`t create product table");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }
        }
    }


    private void showAllData() {
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM test");
            while (res.next()) {
                String name = res.getString("name");
                System.out.println(res.getShort("id") + " " + name);
            }
            res.close();
            st.close();
        } catch (SQLException e) {
            try {
                throw new FoundOfSQLClassException("�� ����� SQL ����� �� ������ �����");//�������� ������� �������
            } catch (FoundOfSQLClassException e1) {
                e1.getMessage();
            }

        }
    }


}
