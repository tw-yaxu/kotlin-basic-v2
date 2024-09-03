import com.thoughtworks.kotlin_basic.ApiClient
import com.thoughtworks.kotlin_basic.service.ProductService
import com.thoughtworks.kotlin_basic.util.PrintUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val apiService = ApiClient()
    val productService = ProductService(apiService)
    val products = async(Dispatchers.IO) { productService.getAllProductWithInventory() }.await()
    val printUtil = PrintUtil()

    val headers = listOf("SKU", "name", "price", "stock quantity", "image URL")
    val rows =  products.map {
        listOf(it.SKU, it.name, it.price.toString(), it.stockQuantity.toString(), it.image)
    }
    printUtil.printTable(headers, rows)
}