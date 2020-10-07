package com.diusframi.tpv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


//Base de datos con los usuarios, los articulos y las categorias
public class BaseDatos extends SQLiteOpenHelper {


    //Constructor
    public BaseDatos(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, "BaseDatos", factory, 1);
    }


    //Modulo que crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {


//Ejecuto las sentencias SQL
        String sentenciaSQL = "CREATE TABLE Usuarios (email TEXT,contrasena TEXT, cif TEXT," +
                " nombrecomercial TEXT, nombrefiscal TEXT, domiciliocomercial TEXT, localidadcomercial TEXT, " +
                "codigopostalcomercial TEXT,provinciacomercial TEXT, telefonocomercial TEXT,domiciliofiscal TEXT," +
                " localidadfiscal TEXT, codigopostalfiscal TEXT, provinciafiscal TEXT, telefonofiscal TEXT, logo BLOB, activo INTEGER);";
        db.execSQL(sentenciaSQL);
        String sentenciaSQL2 = "CREATE TABLE Login (log INTEGER);";
        db.execSQL(sentenciaSQL2);
        String sentenciaSQL3 = "INSERT INTO Usuarios VALUES ('admin','admin','','','','','','','','','','','','','',null,0);";
        db.execSQL(sentenciaSQL3);
        String sentenciaSQL4 = "INSERT INTO Login VALUES (0);";
        db.execSQL(sentenciaSQL4);
        String sentenciaSQL5 = "CREATE TABLE Articulos (Categorias INTEGER, Nombre TEXT, Favorito INTEGER, Precio DOUBLE, IVA INTEGER,Base DOUBLE, CONSTRAINT FK_Categoria FOREIGN KEY (Categorias) REFERENCES  Categoriastabla(id) );";
        db.execSQL(sentenciaSQL5);
        String sentenciaSQL6 = "INSERT INTO Articulos VALUES (1,'Café con leche',1,1.32,10,1.20);";
        db.execSQL(sentenciaSQL6);
        String sentenciaSQL7 = "INSERT INTO Articulos VALUES (2,'Menu del día',1,7.92,10,7.20);";
        db.execSQL(sentenciaSQL7);
        String sentenciaSQL8 = "INSERT INTO Articulos VALUES (3,'Cocacola',1,1.10,10,1);";
        db.execSQL(sentenciaSQL8);
        String sentenciaSQL9 = "INSERT INTO Articulos VALUES (4,'Tarta de queso',1,1.10,10,1);";
        db.execSQL(sentenciaSQL9);
        String sentenciaSQL10 = "INSERT INTO Articulos VALUES (1,'Café1 con leche',1,1.32,10,1.20);";
        db.execSQL(sentenciaSQL10);
        String sentenciaSQL11 = "INSERT INTO Articulos VALUES (1,'Café2 con leche',1,1.32,10,1.20);";
        db.execSQL(sentenciaSQL11);
        String sentenciaSQL12 = "INSERT INTO Articulos VALUES (1,'Café3 con leche',1,1.32,10,1.20);";
        db.execSQL(sentenciaSQL12);
        String sentenciaSQL13 = "CREATE TABLE Categoriastabla (id INTEGER PRIMARY KEY AUTOINCREMENT,Categoria TEXT);";
        db.execSQL(sentenciaSQL13);
        String sentenciaSQL14 = "INSERT INTO Categoriastabla(Categoria) VALUES('Cafés')";
        db.execSQL(sentenciaSQL14);
        String sentenciaSQL15 = "INSERT INTO Categoriastabla(Categoria) VALUES('Menus')";
        db.execSQL(sentenciaSQL15);
        String sentenciaSQL16 = "INSERT INTO Categoriastabla(Categoria) VALUES('Bebidas')";
        db.execSQL(sentenciaSQL16);
        String sentenciaSQL17 = "INSERT INTO Categoriastabla(Categoria) VALUES('Postres')";
        db.execSQL(sentenciaSQL17);


        //El carrito actual
        String sentenciaSQL18 = "CREATE TABLE ArticulosVenta (idarticuloventa INTEGER PRIMARY KEY, Categorias TEXT, Nombre TEXT, Numero INTEGER, Precio DOUBLE,Iva Integer,Base DOUBLE, CONSTRAINT FK_Categoria FOREIGN KEY (Categorias) REFERENCES  Categoriastabla(id));";
        db.execSQL(sentenciaSQL18);

        //Articulos vendidos
        String sentenciaSQL19 = "CREATE TABLE Vendidos (idorden INTEGER, Categorias TEXT, Nombre TEXT, Numero INTEGER, Precio DOUBLE,Iva Integer,Base DOUBLE) ;";
        db.execSQL(sentenciaSQL19);

        //Datos adicionales de los articulos vendidos
        String sentenciaSQL20 = "CREATE TABLE Ordenes (id INTEGER , Fecha Long,FechaTexto String, Hora Long, HoraTexto Long, Total DOUBLE, TipoPago String,Arqueado String,Cambio Double ,CONSTRAINT FK_Orden FOREIGN KEY (id) REFERENCES  Vendidos(idorden));";
        db.execSQL(sentenciaSQL20);

        //Arqueos
        String sentenciaSQL21 = "CREATE TABLE Arqueos (id INTEGER PRIMARY KEY AUTOINCREMENT, NumeroArqueo String, Fecha Long,FechaTexto String, Hora Long, HoraTexto Long,NumeroVentas Integer,Ventas DOUBLE,MovimientosCaja Double,TotalDevoluciones DOUBLE,TotalCalculado Double,SaldoInicial Double,VentasEfectivo Double, Entradas Double, Salidas Double," +
                " Devoluciones Double, CalculadoEfectivo Double, RecuentoEfectivo Double, Descuadre Double, RetiradaEfectivo Double, " +
                "Fianza Double, Efectivo Double, Tarjeta Double, Impuestos10baseimponible Double, Impuestos10cuota Double, Impuestos21baseimponible Double, Impuestos21cuota Double, CONSTRAINT FK_Orden FOREIGN KEY (Id) REFERENCES  Vendidos(idorden));";
        db.execSQL(sentenciaSQL21);


        //Caja registradora (efectivo)
        String sentenciaSQL22 = "CREATE TABLE CuadreCaja (Entradas DOUBLE, Salidas DOUBLE,Fecha Long,Hora Long, HoraTexto Long);";
        db.execSQL(sentenciaSQL22);

        //Texto ticket
        String sentenciaSQL23 = "CREATE TABLE TextoTicketDevolucion (TextoTicket String, TextoDevolucion String,NumeroTicket Integer, NumeroDevolucion Integer);";
        db.execSQL(sentenciaSQL23);

        //Texto ticket inicial
        String sentenciaSQL24 = "INSERT INTO TextoTicketDevolucion (TextoTicket ,TextoDevolucion) VALUES ('T','D');";
        db.execSQL(sentenciaSQL24);

        //Articulos devueltos
        String sentenciaSQL25 = "CREATE TABLE Devueltos (idorden INTEGER, Nombre TEXT, Numero INTEGER,idticket INTEGER) ;";
        db.execSQL(sentenciaSQL25);

        //Articulos devueltos temporal
        String sentenciaSQL26 = "CREATE TABLE Devueltostemporal (idorden INTEGER, Categorias TEXT, Nombre TEXT, Numero INTEGER, Precio DOUBLE,Iva Integer,Base DOUBLE,TipoPago String,idticket INTEGER) ;";
        db.execSQL(sentenciaSQL26);

        //Devoluciones
        String sentenciaSQL27 = "CREATE TABLE Devoluciones (id INTEGER PRIMARY KEY AUTOINCREMENT, Fecha Long,FechaTexto String, Hora Long, HoraTexto Long,Total DOUBLE, TipoPago String,Arqueado String,Cambio Double,idticket INTEGER, CONSTRAINT FK_devolucion FOREIGN KEY (idticket) REFERENCES  Devueltostemporal(idorden));";
        db.execSQL(sentenciaSQL27);

        //Saldo inicial
        String sentenciaSQL28 = "CREATE TABLE SaldoInicial (SaldoInicial DOUBLE,idticket INTEGER);";
        db.execSQL(sentenciaSQL28);
        //Para el siguiente numero de ticket en crear cuenta hacer una tabla y si hay el numero ahi lo coge, cuando lo coge lo elimina, asi el siguiente si mira esa tabla esta vacia, table ticket y devolucion entonces si coges uno, pones null ese y dejas el otro
    }


    //Funcion para insertar texto de ticket
    public void borrarnumeroticket() {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET NumeroTicket = 0";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();




        statement.executeInsert();
    }

    //Funcion para insertar texto de ticket
    public void borrarnumerodevolucion() {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET NumeroDevolucion = 0";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();




        statement.executeInsert();
    }


    //Funcion para insertar texto de ticket
    public void inserttextoticket(String textoticket) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET TextoTicket = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, textoticket);


        statement.executeInsert();
    }

    //Funcion para insertar texto de ticket
    public void insertnumeroticket(int numeroticket) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET NumeroTicket = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.bindLong(1, numeroticket);

        statement.executeInsert();
    }

    //Funcion para insertar texto de ticket
    public void inserttextodevolucion(String textodevolucion) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET TextoDevolucion = ?";


        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, textodevolucion);


        statement.executeInsert();
    }

    //Funcion para insertar texto de ticket
    public void insertnumerodevolucion(int numerodevolucion) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TextoTicketDevolucion SET  NumeroDevolucion = ?";


        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindLong(1, numerodevolucion);

        statement.executeInsert();
    }


//insert saldo inicial, contiene numerodeticket, para mostrar busca el saldo inicial del ticket anterior
public void saldoinicial (Double saldoinicial,Integer idticket){
    SQLiteDatabase database = getWritableDatabase();
    String sql = "INSERT INTO SaldoInicial VALUES (?, ?)";

    SQLiteStatement statement = database.compileStatement(sql);
    statement.clearBindings();

    statement.bindDouble(1, saldoinicial);
    statement.bindLong(2, idticket);



    statement.executeInsert();
}

    //Funcion para actualizar los datos de un usuario
    public void UpdateDataUsuarios(String email, String contrasena, String cif, String nombrecomercial, String nombrefiscal,
                                   String domiciliocomercial, String localidadcomercial, String codigopostalcomercial, String provinciacomercial, String telefonocomercial,
                                   String domiciliofiscal, String localidadfiscal, String codigopostalfiscal, String provinciafiscal, String telefonofiscal,byte[] logo, Integer activo) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "Update Usuarios Set email=?, contrasena=?, cif=?, nombrecomercial=?, nombrefiscal=?, domiciliocomercial=?, localidadcomercial=?, codigopostalcomercial=?, provinciacomercial=?" +
                ", telefonocomercial=?, domiciliofiscal=?, localidadfiscal=?, codigopostalfiscal=?, provinciafiscal=?, telefonofiscal=?, logo=?, activo = ? WHERE email = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, email);
        statement.bindString(2, contrasena);
        statement.bindString(3, cif);
        statement.bindString(4, nombrecomercial);
        statement.bindString(5, nombrefiscal);
        statement.bindString(6, domiciliocomercial);
        statement.bindString(7, localidadcomercial);
        statement.bindString(8, codigopostalcomercial);
        statement.bindString(9, provinciacomercial);
        statement.bindString(10, telefonocomercial);
        statement.bindString(11, domiciliofiscal);
        statement.bindString(12, localidadfiscal);
        statement.bindString(13, codigopostalfiscal);
        statement.bindString(14, provinciafiscal);
        statement.bindString(15, telefonofiscal);
        statement.bindBlob(16, logo);
        statement.bindDouble(17, activo);
        statement.bindString(18, email);


        statement.executeUpdateDelete();
    }


    //Funcion para actualizar los datos de un usuario
    public void UpdateDataUsuariossinimagen(String email, String contrasena, String cif, String nombrecomercial, String nombrefiscal,
                                   String domiciliocomercial, String localidadcomercial, String codigopostalcomercial, String provinciacomercial, String telefonocomercial,
                                   String domiciliofiscal, String localidadfiscal, String codigopostalfiscal, String provinciafiscal, String telefonofiscal, Integer activo) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "Update Usuarios Set email=?, contrasena=?, cif=?, nombrecomercial=?, nombrefiscal=?, domiciliocomercial=?, localidadcomercial=?, codigopostalcomercial=?, provinciacomercial=?" +
                ", telefonocomercial=?, domiciliofiscal=?, localidadfiscal=?, codigopostalfiscal=?, provinciafiscal=?, telefonofiscal=?, logo = null,activo = ? WHERE email = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, email);
        statement.bindString(2, contrasena);
        statement.bindString(3, cif);
        statement.bindString(4, nombrecomercial);
        statement.bindString(5, nombrefiscal);
        statement.bindString(6, domiciliocomercial);
        statement.bindString(7, localidadcomercial);
        statement.bindString(8, codigopostalcomercial);
        statement.bindString(9, provinciacomercial);
        statement.bindString(10, telefonocomercial);
        statement.bindString(11, domiciliofiscal);
        statement.bindString(12, localidadfiscal);
        statement.bindString(13, codigopostalfiscal);
        statement.bindString(14, provinciafiscal);
        statement.bindString(15, telefonofiscal);
        statement.bindDouble(16, activo);
        statement.bindString(17, email);


        statement.executeUpdateDelete();
    }
    //Funcion para crear un usuario
    public void insertDataUsuarios(String email, String contrasena, String cif, String nombrecomercial, String nombrefiscal,
                                   String domiciliocomercial, String localidadcomercial, String codigopostalcomercial, String provinciacomercial, String telefonocomercial,
                                   String domiciliofiscal, String localidadfiscal, String codigopostalfiscal, String provinciafiscal, String telefonofiscal, byte[] logo) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, email);
        statement.bindString(2, contrasena);
        statement.bindString(3, cif);
        statement.bindString(4, nombrecomercial);
        statement.bindString(5, nombrefiscal);
        statement.bindString(6, domiciliocomercial);
        statement.bindString(7, localidadcomercial);
        statement.bindString(8, codigopostalcomercial);
        statement.bindString(9, provinciacomercial);
        statement.bindString(10, telefonocomercial);
        statement.bindString(11, domiciliofiscal);
        statement.bindString(12, localidadfiscal);
        statement.bindString(13, codigopostalfiscal);
        statement.bindString(14, provinciafiscal);
        statement.bindString(15, telefonofiscal);
        statement.bindBlob(16, logo);
        statement.bindDouble(17, 1);


        statement.executeInsert();
    }


    //Hacer recuento de la caja y comprobar con ventas si es lo mismo
    public void CrearArqueo(String NumeroArqueo, Integer NumeroVentas, Double Ventas, Double MovimientosCaja, Double TotalDevoluciones,
                            Double TotalCalculado, Double SaldoInicial, Double VentasEfectivo, Double Entradas, Double Salidas,
                            Double Devoluciones, Double CalculadoEfectivo, Double RecuentoEfectivo, Double Descuadre, Double RetiradaEfectivo,
                            Double Fianza, Double Efectivo, Double Tarjeta, Double Impuestos10baseimponible, Double Impuestos10cuota, Double Impuestos21baseimponible, Double Impuestos21cuota) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String diatexto = df.format(c);
        long dia = Long.parseLong(diatexto);
        SimpleDateFormat df2 = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String hora = df2.format(c);
        long horad =Long.parseLong(hora);
        SimpleDateFormat df3 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hora2 = df3.format(c);



        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Arqueos Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.bindString(2, NumeroArqueo);
        statement.bindLong(3, dia);
        statement.bindString(4, diatexto);
        statement.bindLong(5, horad);
        statement.bindString(6, hora2);
        statement.bindDouble(7, NumeroVentas);
        statement.bindDouble(8, Ventas);
        statement.bindDouble(9, MovimientosCaja);
        statement.bindDouble(10, TotalDevoluciones);
        statement.bindDouble(11, TotalCalculado);
        statement.bindDouble(12, SaldoInicial);
        statement.bindDouble(13, VentasEfectivo);
        statement.bindDouble(14, Entradas);
        statement.bindDouble(15, Salidas);
        statement.bindDouble(16, Devoluciones);
        statement.bindDouble(17, CalculadoEfectivo);
        statement.bindDouble(18, RecuentoEfectivo);
        statement.bindDouble(19, Descuadre);
        statement.bindDouble(20, RetiradaEfectivo);
        statement.bindDouble(21, Fianza);
        statement.bindDouble(22, Efectivo);
        statement.bindDouble(23, Tarjeta);
        statement.bindDouble(24, Impuestos10baseimponible);
        statement.bindDouble(25, Impuestos10cuota);
        statement.bindDouble(26, Impuestos21baseimponible);
        statement.bindDouble(27, Impuestos21cuota);


        statement.executeUpdateDelete();


    }


    //Borrar ticket
    public void BorrarTicket() {

       SQLiteDatabase database = getWritableDatabase();
        String sql= "DELETE FROM ArticulosVenta";
        SQLiteStatement statement3 = database.compileStatement(sql);

          statement3.clearBindings();


        statement3.executeUpdateDelete();
    }



    //Funcion para meter todos los datos complementarios a una devolucion
    public void anadirdatosdevolucion(Integer id,Double Total, Integer idticket,String tipopago) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String diatexto = df.format(c);
        long dia = Long.parseLong(diatexto);
        SimpleDateFormat df2 = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String hora = df2.format(c);
        long horad =Long.parseLong(hora);
        SimpleDateFormat df3 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hora2 = df3.format(c);

        SQLiteDatabase database = getWritableDatabase();
        String sql2 = "INSERT INTO Devoluciones (id,Fecha,FechaTexto,Hora,HoraTexto,Total,idticket,TipoPago) VALUES (?,?,?,?,?,?,?,?)";


        SQLiteStatement statement2 = database.compileStatement(sql2);



        statement2.clearBindings();



        statement2.bindLong(1, id);
        statement2.bindLong(2, dia);
        statement2.bindString(3, diatexto);
        statement2.bindLong(4, horad);
        statement2.bindString(5, hora2);
        statement2.bindDouble(6, Total);
        statement2.bindLong(7, idticket);
        statement2.bindString(8, tipopago);

        statement2.executeInsert();

    }


    //Funcion para crear nueva orden y borra el carrito
    public void actualizardevoluciontemporal(Integer numero, String nombre ,int idorden) {

        SQLiteDatabase database = getWritableDatabase();
        String sql1 = "UPDATE Devueltostemporal SET Numero = ? WHERE Nombre = ? AND idorden = ?";

        SQLiteStatement statement = database.compileStatement(sql1);


        statement.clearBindings();



        statement.bindLong(1, numero);
        statement.bindString(2, nombre);
        statement.bindLong(3, idorden);



        String sql2 = "UPDATE Devueltos SET Numero = ? WHERE Nombre = ? AND idorden = ?";

        SQLiteStatement statement2 = database.compileStatement(sql2);


        statement2.clearBindings();





        statement2.bindLong(1, numero);
        statement2.bindString(2, nombre);
        statement2.bindLong(3, idorden);


        statement2.executeInsert();
        statement.executeInsert();


    }


    //Funcion para meter todos los datos complementarios a una orden
    public void anadirdatosorden(int id,Double Total) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String diatexto = df.format(c);
        long dia = Long.parseLong(diatexto);
        SimpleDateFormat df2 = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String hora = df2.format(c);
        long horad =Long.parseLong(hora);
        SimpleDateFormat df3 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hora2 = df3.format(c);



        SQLiteDatabase database = getWritableDatabase();
        String sql2 = "INSERT INTO Ordenes (id,Fecha,FechaTexto,Hora,HoraTexto,Total) VALUES (?,?,?,?,?,?)";
        String sql3 = "DELETE FROM ArticulosVenta";

        SQLiteStatement statement2 = database.compileStatement(sql2);
        SQLiteStatement statement3 = database.compileStatement(sql3);


        statement2.clearBindings();
        statement3.clearBindings();

        statement2.bindLong(1, id);
        statement2.bindLong(2, dia);
        statement2.bindString(3, diatexto);
        statement2.bindLong(4, horad);
        statement2.bindString(5, hora2);
        statement2.bindDouble(6, Total);


        statement2.executeInsert();
        statement3.executeUpdateDelete();
    }

    //Funcion para crear nueva orden y borra el carrito
    public void crearnuevadevoluciontemporal(int idorden, String categoria, String nombre, int numero, double precio, int iva,int idticket) {

        SQLiteDatabase database = getWritableDatabase();


        String sql2 = "INSERT INTO Devueltostemporal (idorden,Categorias, Nombre, Numero, Precio, Iva,Base,idticket) VALUES (?,?,?,?,?,?,?,?)";
        String sql3 = "INSERT INTO Devueltos (idorden,Nombre, Numero,idticket) VALUES (?,?,?,?)";

        SQLiteStatement statement2 = database.compileStatement(sql2);
        statement2.clearBindings();

        double Base = precio - ((precio * 10) / 100);


        statement2.bindLong(1, idorden);
        statement2.bindString(2, categoria);
        statement2.bindString(3, nombre);
        statement2.bindDouble(4, numero);
        statement2.bindDouble(5, precio);
        statement2.bindDouble(6, iva);
        statement2.bindDouble(7, Base);
        statement2.bindLong(8, idticket);




        SQLiteStatement statement = database.compileStatement(sql3);
        statement.clearBindings();


        statement.bindLong(1, idorden);
        statement.bindString(2, nombre);
        statement.bindLong(3, numero);
        statement.bindLong(4, idticket);


        statement2.executeInsert();
        statement.executeInsert();


    }



    //Funcion para crear nueva orden y borra el carrito
    public void crearnuevaorden(int idorden, String categoria, String nombre, int numero, double precio, int iva) {

        SQLiteDatabase database = getWritableDatabase();
        String sql1 = "INSERT INTO Vendidos (idorden, Categorias, Nombre, Numero, Precio, Iva,Base) VALUES (?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql1);


        statement.clearBindings();



        double Base = precio - ((precio * 10) / 100);

        statement.bindDouble(1, idorden);
        statement.bindString(2, categoria);
        statement.bindString(3, nombre);
        statement.bindDouble(4, numero);
        statement.bindDouble(5, precio);
        statement.bindDouble(6, iva);
        statement.bindDouble(7, Base);


        statement.executeInsert();



    }


    //Funcion para crear orden en efectivo
    public void ordenefectivo(Double cambio, int idorden) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Ordenes Set TipoPago = 'Efectivo',Cambio = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.clearBindings();

        statement.bindDouble(1, cambio);
        statement.bindDouble(2, idorden);


        statement.executeInsert();


    }

    //Funcion para crear orden en tarjeta
    public void ordentarjeta(int idorden) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Ordenes Set TipoPago = 'Tarjeta' WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.clearBindings();


        statement.bindDouble(1, idorden);


        statement.executeInsert();


    }

    //Funcion para actualizar si es favorito o no
    public void actualizafavorito(int favorito, String nombre) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Articulos Set Favorito = ? WHERE Nombre = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, favorito);
        statement.bindString(2, nombre);

        statement.executeUpdateDelete();
    }


    //Funcion para ingresar en la caja registradora
    public void actualizarcajaingresar(Double dinero) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String diatexto = df.format(c);
        long dia = Long.parseLong(diatexto);
        Date c3 = Calendar.getInstance().getTime();
        SimpleDateFormat df3 = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String diatexto2 = df3.format(c3);
        long hora = Long.parseLong(diatexto2);
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hora2 = df2.format(c);

        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "INSERT INTO CuadreCaja (Entradas,Salidas,Fecha,Hora,HoraTexto) VALUES (?,?,?,?,?)";


        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, dinero);
        statement.bindDouble(2, 0.0);
        statement.bindLong(3, dia);
        statement.bindLong(4, hora);
        statement.bindString(5, hora2);


        statement.executeInsert();


    }

    //Funcion para retirar de la caja registradora
    public void actualizarcajaretirar(Double dinero) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String diatexto = df.format(c);
        long dia = Long.parseLong(diatexto);
        Date c3 = Calendar.getInstance().getTime();
        SimpleDateFormat df3 = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String diatexto2 = df3.format(c3);
        long hora = Long.parseLong(diatexto2);
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hora2 = df2.format(c);

        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "INSERT INTO CuadreCaja (Entradas,Salidas,Fecha,Hora,HoraTexto) VALUES (?,?,?,?,?)";


        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, 0.0);
        statement.bindDouble(2, dinero);
        statement.bindLong(3, dia);
        statement.bindLong(4, hora);
        statement.bindString(5, hora2);


        statement.executeInsert();

    }

    //Funcion para actualizar numero de articulos de producto de lista de la compra
    public void actualizarnumeroarticulos(String nombre, int numero) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE ArticulosVenta Set Numero = ? WHERE Nombre = ?";
        String sql2 = "DELETE FROM ArticulosVenta WHERE Numero = 0";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, numero);
        statement.bindString(2, nombre);
        statement.executeUpdateDelete();


        SQLiteStatement statement2 = database.compileStatement(sql2);
        statement2.clearBindings();


        statement2.executeUpdateDelete();
    }

    //Funcion para quitar articulo de la lista de la compra si su numero es 0
    public void quitararticulo() {

        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM ArticulosVenta WHERE Numero = 0";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.executeUpdateDelete();
    }

    //Funcion para poner articulo nuevo en lista de la compra
    public void articulonuevolistacompra(String categoria, String nombre, int numero, Double precio, int iva) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ArticulosVenta (Categorias, Nombre, Numero, Precio, Iva, Base) VALUES (?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        double Base = precio - ((precio * 10) / 100);

        statement.bindString(1, categoria);
        statement.bindString(2, nombre);
        statement.bindDouble(3, numero);
        statement.bindDouble(4, precio);
        statement.bindDouble(5, iva);
        statement.bindDouble(6, Base);


        statement.executeInsert();
    }

    //Funcion para crear un articulo
    public void Creararticulo(int Categorias, String nombre, int favorito, Double precio, int iva) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Articulos(Categorias,Nombre,Favorito,Precio,IVA,Base) VALUES (?,?,?,?,?,?)";
        double Base;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        if (precio == null) {
            precio = 0.0;
            Base = 0.0;
        } else {
            Base = precio - ((precio * 10) / 100);
        }


        statement.bindDouble(1, Categorias);
        statement.bindString(2, nombre);
        statement.bindDouble(3, favorito);
        statement.bindDouble(4, precio);
        statement.bindDouble(5, iva);
        statement.bindDouble(6, Base);


        statement.executeInsert();
    }

    //Funcion para crear un articulo
    public void Creararticulovariable(String Categorias, String nombre, int favorito, Double precio, int iva) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Articulos(Categorias,Nombre,Favorito,Precio,IVA,Base) VALUES (?,?,?,?,?,?)";
        double Base;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        if (precio == null) {
            precio = 0.0;
            Base = 0.0;
        } else {
            Base = precio - ((precio * 10) / 100);
        }
        SQLiteDatabase databaseread = getReadableDatabase();
        int categoriafinal = 0;
        Cursor cursortipos = databaseread.rawQuery("SELECT id From Categoriastabla WHERE Categoria ='" + Categorias + "'", null);
        if (cursortipos.moveToNext()) {
            categoriafinal = cursortipos.getInt(0);
        }

        statement.bindLong(1, categoriafinal);
        statement.bindString(2, nombre);
        statement.bindDouble(3, favorito);
        statement.bindDouble(4, precio);
        statement.bindDouble(5, iva);
        statement.bindDouble(6, Base);


        statement.executeInsert();
    }
    public void Updatearticulo(String categoria, String nombre, Double precio, int iva) {
        SQLiteDatabase database = getWritableDatabase();
        SQLiteDatabase databaseread = getReadableDatabase();
        Integer categoriafinal = null;
        Cursor cursortipos = databaseread.rawQuery("SELECT id From Categoriastabla WHERE Categoria ='" + categoria + "'", null);
        if (cursortipos.moveToNext()) {
            categoriafinal = Integer.parseInt(cursortipos.getString(0));
        }

        cursortipos.close();
        String sql = "UPDATE Articulos SET Categorias = '" + categoriafinal + "',Precio = ?,IVA = ?,Base = ? WHERE Nombre = ?";
        SQLiteStatement statement = database.compileStatement(sql);


        statement.clearBindings();

        double Base = precio - ((precio * 10) / 100);


        statement.bindDouble(1, precio);
        statement.bindDouble(2, iva);
        statement.bindDouble(3, Base);
        statement.bindString(4, nombre);

        statement.executeUpdateDelete();
    }

    //Funcion para crear una categoria
    public void CrearCategoria(String categoria) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Categoriastabla(Categoria) VALUES (?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, categoria);


        statement.executeInsert();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}