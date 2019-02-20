use std::io; //uses the io library from the standard library
use std::cmp::Ordering; //uses Ordering from the standard libary
use rand::Rng; //uses the rand crate

fn main() {
    println!("Guess the number!");

    let secret_number = rand::thread_rng().gen_range(1, 101); //gives us a random number generator, then use gen_range to generate a number between 1 and 100

    println!("The secret number is: {}", secret_number); //placeholder

    loop{
        println!("Please input your guess.");

        let mut guess = String::new(); //mutable variable

        io::stdin().read_line(&mut guess)
            .expect("Failed to read line"); //error handling

        let guess: u32 = match guess.trim().parse() { //shadow variable lets us reuse name, trim eliminates whitespace, and parse converts the string into a number
            Ok(num) => num,
            Err(_) => continue, //if error, continue to make guesses
        };

        println!("You guessed: {}", guess);

        match guess.cmp(&secret_number){ //compares to values of any type
            Ordering::Less => println!("Too small!"), //if guess is less than random number
            Ordering::Greater => println!("Too big!"), //if guess is greater than random number
            Ordering::Equal => { //if guess is the random number
                println!("You win!");
                break;
            }
        }
    }
}
