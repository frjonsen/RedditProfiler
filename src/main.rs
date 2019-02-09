mod reddit;
#[macro_use]
extern crate getset;

use reddit::model;

fn main() {
    let listing = reddit::model::Listing {
        after: None,
        before: Some("Hello".to_owned()),
        modhash: "Something".to_owned(),
        children: vec![],
    };
}
