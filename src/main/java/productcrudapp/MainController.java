package productcrudapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import .servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.view.RedirectView;

import productcrudapp.dao.ProductDao;
import productcrudapp.model.Product;

@Controller
public class MainController {

	@Autowired
	private ProductDao productDao;

	@RequestMapping("/")
	public String home(Model m) {
		List<Product> products = productDao.getAllProduct();
		m.addAttribute("products", products);
		return "index";
	}

//	show add product form
	@RequestMapping("/add-product")
	public String addProduct(Model m) {
		m.addAttribute("title", "Add Product");
		return "addProduct";
	}

//	handle product form 
	@RequestMapping(value = "/handle-product", method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product, HttpServletRequest req) {
		System.out.println(product);
		productDao.createProduct(product);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(req.getContextPath() + "/");
		return redirectView;
	}

	@RequestMapping("/delete/{productId}")
	public RedirectView deleteHandler(@PathVariable("productId") int pid) {

		this.productDao.deleteProduct(pid);
		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl(req.getContextPath() + "/");
		return redirectView;
	}

	@RequestMapping("/update/{productId}")
	public String updateProduct(@PathVariable("productId") int pid, Model m) {
		Product product = this.	productDao.getProduct(pid);
		m.addAttribute("product", product);
		m.addAttribute("title", "Update Product");
		return "updateProduct";
	}
}
