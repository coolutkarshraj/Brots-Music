import * as _ from "underscore";

export class $Model {
    public static model1(brand, products) {
        const p = _.map(_.where(products, {brandId: brand.id}), (p) => {
            delete p.brandId, delete p.brandName;
            return p;
        });
        return {
            brandId: brand.id,
            brandName: brand.name,
            products: p
        };
    }
    public static model2(products) {
        const brands = [];
        const brandIds = _.uniq(_.map(products, (p) => {
            return p.brandId;
        }));
        const brandNames = _.uniq(_.map(products, (p) => {
            return p.brandName;
        }));
        _.each(brandIds, (id, i) => {
            brands.push({id: id, name: brandNames[i]});
        });
        return brands;
    }
}