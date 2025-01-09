 document.querySelectorAll('.button').forEach(button => {
        button.onclick = () => {
            const hotelName = button.getAttribute('data-hotel');
            document.getElementById('hotelName').innerText = hotelName;
            document.getElementById('bookingModal').style.display = 'block';
            updatePrice();
        };
    });

    // Close modal
    document.querySelector('.close').onclick = () => {
        document.getElementById('bookingModal').style.display = 'none';
    };

    // Close modal if outside is clicked
    window.onclick = (event) => {
        if (event.target == document.getElementById('bookingModal')) {
            document.getElementById('bookingModal').style.display = 'none';
        }
    };

    // Update price based on selections
    const updatePrice = () => {
        const roomType = document.getElementById('roomType').value;
        const bedType = document.getElementById('bedType').value;

        let price = 0;
        
        if (roomType === 'ac') {
            price += 100; // Base price for AC
        } else {
            price += 70; // Base price for Non-AC
        }

        if (bedType === 'double') {
            price += 30; // Additional cost for Double Bed
        }

        document.getElementById('price').innerText = price;
    };

    // Update price on change
    document.getElementById('roomType').onchange = updatePrice;
    document.getElementById('bedType').onchange = updatePrice;

    // Confirm booking
    document.getElementById('bookingForm').onsubmit = (event) => {
        event.preventDefault();
        alert(`Booking confirmed at ${document.getElementById('hotelName').innerText}! Total Price: $${document.getElementById('price').innerText}`);
        document.getElementById('bookingModal').style.display = 'none';
    };