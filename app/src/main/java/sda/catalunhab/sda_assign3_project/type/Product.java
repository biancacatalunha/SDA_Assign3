package sda.catalunhab.sda_assign3_project.type;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DCU - SDA - Assignment 3
 *
 * Represents each item of the product list, containing a name, price and image
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
@Data
@AllArgsConstructor
public class Product {

    private String name;

    private String price;

    private int image;
}
