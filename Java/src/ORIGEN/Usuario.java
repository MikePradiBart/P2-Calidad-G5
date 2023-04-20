package ORIGEN;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author paula
 */
public class Usuario {

    private String nombre;
    private String nickname;
    private String contrasena;

    private Personaje personaje;


    private  int oro;

    private boolean baneado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public boolean isBaneado() {
        return baneado;
    }

    public void setBaneado(boolean baneado) {
        this.baneado = baneado;
    }

    //Constructor
    public Usuario (String nom, String nick, String password){
        this.nombre = nom;
        this.nickname = nick;
        this.contrasena = password;
        this.baneado = false;
        this.oro = 500;
        this.personaje = null;
    }

    public Personaje getPersonaje(){
        return this.personaje;
    }

    public void setPersonaje(Personaje pj){
        this.personaje = personaje;
    }
}