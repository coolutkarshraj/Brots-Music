import {Tables} from "../database/Tables";

export class $Query {
    // public static getQuery1(distributorId) {
    //     return `select ps.id, p.id 'productId', b.id 'brandId', p.name 'productName', b.name 'brandName', p.imageUrl 'productImageUrl', p.type 'productType', p.mrp 'MRP', 
    //                     p.discountRate, p.color, p.taste, c.id 'categoryId', c.name 'categoryName'
    //                     from ${Tables.products} ps
    //                     join ${Tables.product} p on p.id = ps.productId
    //                     join ${Tables.category} c on c.id = p.categoryId
	// 					join ${Tables.brand} b on b.id = p.brandId
    //                     where ps.userId = ${distributorId};`;
    // }

    // public static getQuery2(distributorId) {
    //     return `select 
    //                     o.consumerId, concat(u.firstName, ' ', u.middleName, ' ', u.lastName) 'consumerName', 
    //                     u.phone, u.gender, u.address, u.imageUrl 'consumerImageUrl', 
    //                     o.productId, p.name 'productName', p.imageUrl 'productImageUrl', o.quantity, o.weight 'weightInGrams', o.volume 'volumeInML', 
    //                     p.type 'productType', p.mrp 'MRP', p.discountRate, p.color, p.taste,
    //                     b.name 'brandName', c.name 'categoryName'
    //                     from ${Tables.orders} o
    //                     join ${Tables.user} u on u.id = o.consumerId
    //                     join ${Tables.product} p on p.id = o.productId
    //                     join ${Tables.brand} b on b.id = p.brandId
    //                     join ${Tables.category} c on c.id = p.categoryId
    //                     where distributorId = ${distributorId} and o.status = 0;`;
    // }

    // public static getQuery3(distributorId) {
    //     return `select i.*, os.price 'orderPrice' from ${Tables.inbox} i
    //                     left join ${Tables.orders} os on os.id = i.orderId
    //                     where i.receiverId = ${distributorId};`;
    // }
}