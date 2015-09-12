package controllers;

import play.mvc.With;

/**
 * Created by Azat on 10.09.2015.
 */
@Check("admin")
@With(Secure.class)
public class Users extends CRUD {
}
