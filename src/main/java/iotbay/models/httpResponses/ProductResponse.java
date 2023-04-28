package iotbay.models.httpResponses;

import iotbay.models.Product;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
public class ProductResponse implements Serializable {
    int limit;
    int nextOffset;
    int prevOffset;
    int numberOfPages;
    int currentPage;
    int lastOffset;
    List<Product> products;

}
