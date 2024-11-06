document.addEventListener('DOMContentLoaded', function() {
    loadContent('/views/client/content/content.html');
});

function loadContent(url) {
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Có lỗi xảy ra');
            }
            return response.text();
        })
        .then(data => {
            document.getElementById('content').innerHTML = data;
        })
        .catch(error => {
            console.error('Có lỗi xảy ra:', error);
        });
}


//call api sản phẩm
document.addEventListener('DOMContentLoaded', function() {
    loadData();
});

const loadData = async () => {
    try {
        const response = await fetch('/home/content', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();
            const { products, categories } = data; // Lấy cả products và categories từ phản hồi API

            displayProducts(products);      // Hiển thị sản phẩm
            displayCategories(categories);  // Hiển thị danh mục
        } else {
            alert(`Lỗi: ${await response.text()}`);
        }
    } catch (error) {
        console.error("Lỗi khi tải dữ liệu:", error);
    }
};

const displayCategories = (categories) => {
    const categoryHeader = document.getElementById('category-header');
    categoryHeader.innerHTML = ''; // Xóa nội dung cũ

    categories.forEach(({ id, name }) => {
        const categoryHtml = `
             <li>
                <a href="">${name}</a>
            </li>
        `;
        categoryHeader.insertAdjacentHTML('beforeend', categoryHtml);
    });

};

const displayProducts = (products) => {
    const productHeader = document.getElementById('product-header');
    productHeader.innerHTML = ''; // Xóa nội dung cũ

    products.forEach(({ id, name }) => {
        const productHtml = `
            <li>
                <a href="#" onclick="selectProduct(${id})">
                    <span>${name}<span class="tip tip-new">New</span></span>
                </a>
            </li>
        `;
        productHeader.insertAdjacentHTML('beforeend', productHtml);
    });

    const productList = document.getElementById('product-list');
    productList.innerHTML = ''; // Xóa nội dung cũ

    products.forEach(({ id, name, image, price, details }) => {
        const productHtml = `
            <div class="product-item furniture col-6 col-md-4 col-lg-3">
                <div class="product product-4">
                    <figure class="product-media">
                        <a href="#">
                            <img src="/images/product/${image}" class="product-image" alt="${name}">
                        </a>
                    </figure>
                    <div class="product-body">
                        <h3 class="product-title"><a href="#">${name}</a></h3>
                        <h3 class="product-title">Giá: ${price} VND</h3>
                        <h3 class="product-title"><a href="#">${details}</a></h3><br>
                        <div class="product-action">
                            <button type="button" class="btn-product btn-cart" onclick="addToCart(${id})">
                                <span>Thêm vào giỏ hàng</span>
                                <i class="icon-long-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        productList.insertAdjacentHTML('beforeend', productHtml);
    });

    //sản phẩm ben trang category
    const PCList = document.getElementById('product-category');
    PCList.innerHTML = ''; // Xóa nội dung cũ

    products.forEach(({ id, image, price, details }) => {
        const productHtml = `
            <div class="col-6" >
                <div class="product product-7 text-center">
                    <figure class="product-media">
                        <span class="product-label label-new">New</span>
                        <a>
                            <img src="/images/product/${image}" class="product-image" alt="">
                        </a>
                        <div class="product-action">
                          
                            <button  class="btn-product btn-cart" onclick="addToCart(${id})">
                            <span>Thêm vào giỏ hàng</span></button>
                          
                        </div>
                    </figure>
    
                    <div class="product-body">
    
                        <h3 class="product-title"><a >${details}</a></h3><!-- End .product-title -->
                        <div class="product-price" >
                           ${price} VND
                        </div><!-- End .product-price -->
                        <div class="ratings-container">
                            <div class="ratings">
                                <div class="ratings-val" style="width: 20%;"></div>
                            </div>
                            <span class="ratings-text">( 2 Reviews )</span>
                        </div>
    
                    </div>
                </div>
            </div><!-- End .col-sm-6 -->
         `;

        PCList.insertAdjacentHTML('beforeend', productHtml);
    });
};
// Thêm sản phẩm vào giỏ hàng
const addToCart = (id) => {
    console.log(`Thêm sản phẩm có ID: ${id} vào giỏ hàng`);

    // Gửi yêu cầu đến API để thêm sản phẩm vào giỏ hàng
    fetch('/home/addcart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ productId: id }), // Gửi ID sản phẩm
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // Nếu thành công, trả về thông điệp
            } else {
                throw new Error('Có lỗi khi thêm sản phẩm vào giỏ hàng');
            }
        })
        .then(message => {
            console.log(message); // Hiển thị thông điệp thành công
            displayCart(); // Cập nhật giỏ hàng
        })
        .catch(error => {
            console.error('Có lỗi:', error);
        });
};



const selectProduct = (id) => {
    console.log(`Sản phẩm được chọn có ID: ${id}`);
    // Xử lý khi người dùng chọn sản phẩm
};






