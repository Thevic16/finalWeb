package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.LoginResponse;
import edu.pucmm.eict.models.FormApi;
import edu.pucmm.eict.models.MyRole;
import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class ApiRestController extends BaseController {

    //Llave de 32 bytes por la firma utilizada
    public final static String LLAVE_SECRETA = "asd12D1234dfr123@#4Fsdcasdd5g78a";


    public ApiRestController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.routes(() ->{
            path("/api",() ->{

                path("/v1",() ->{

                    path("/login",() ->{

                        //verify if user correct
                        post("/", ctx -> {
                            String userName = ctx.formParam("userName");
                            String password = ctx.formParam("password");
                            Boolean isCorrect = verifyUser(userName,password);

                            if(isCorrect){
                                ctx.sessionAttribute("userApi",userName);
                                ctx.json(generationJsonWebToken(userName));
                            }
                            else {
                                throw new UnauthorizedResponse( "Nombre de usuario o contraseña incorrecto!");
                            }

                        },roles(MyRole.DEFAULT));

                    });

                    //API REST
                    path("/form",() ->{

                        before(ctx -> {
                            //informacion para consultar en la trama.
                            String header = "Authorization";
                            String prefix = "Bearer";

                            String headerAutentification = ctx.header(header);
                            if(headerAutentification ==null || !headerAutentification.startsWith(prefix)){
                                throw new ForbiddenResponse( "No tiene permiso para acceder al recurso, no enviando header de autorización");
                            }

                            //recuperando el token y validando
                            String tramaJwt = headerAutentification.replace(prefix, "");
                            try {
                                Claims claims = Jwts.parser()
                                        .setSigningKey(Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes()))
                                        .parseClaimsJws(tramaJwt).getBody();
                                //mostrando la información para demostración.
                                System.out.println("Mostrando el JWT recibido: " + claims.toString());
                            }catch (ExpiredJwtException | MalformedJwtException | SignatureException e){ //Excepciones comunes
                                throw new ForbiddenResponse( e.getMessage());
                            }

                        });


                        get("/:userName", ctx -> {
                            String userName = ctx.pathParam("userName");
                            List<FormApi> filteredForms = FormApi.getFilteredForms(userName);

                            ctx.json(filteredForms);
                        },roles(MyRole.DEFAULT));

                        post("/", ctx -> {
                            // parsing the POJO information must come in json format.
                            FormApi tmp = ctx.bodyAsClass(FormApi.class);
                            //create.
                            ctx.json(tmp);
                        },roles(MyRole.DEFAULT));

                        after(ctx -> {
                            ctx.header("Content-Type", "application/json");
                        });

                    });


                });

            });

        });

    }

    public Boolean verifyUser(String userName, String password){
        Boolean isCorrect = false;
        List<User> users = UserServices.getInstance().findAll();

        for (User user:users) {
            if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                isCorrect = true;
                return isCorrect;
            }
        }

        return isCorrect;
    }


    /**
     * Metodo para la generación de la trama JWT
     * @param userName
     * @return
     */
    private static LoginResponse generationJsonWebToken(String userName){
        LoginResponse loginResponse = new LoginResponse();
        //generando la llave.
        SecretKey secretKey = Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes());
        //Generando la fecha valida
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10);
        System.out.println("La fecha actual: "+localDateTime.toString());

        //
        Date expirationDate = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4)));
        // creando la trama.
        String jwt = Jwts.builder()
                .setIssuer("PUCMM-ECT")
                .setSubject("finalWeb")
                .setExpiration(expirationDate)
                .claim("user", userName)
                .signWith(secretKey)
                .compact();
        loginResponse.setToken(jwt);
        loginResponse.setExpires_in(expirationDate.getTime());

        return loginResponse;
    }


}
