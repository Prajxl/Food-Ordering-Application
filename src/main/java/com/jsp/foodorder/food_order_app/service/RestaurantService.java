package com.jsp.foodorder.food_order_app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;
import com.jsp.foodorder.food_order_app.entity.Restaurent;
import com.jsp.foodorder.food_order_app.exception.IdNotFoundException;
import com.jsp.foodorder.food_order_app.exception.NoRecordAvailableException;
import com.jsp.foodorder.food_order_app.repository.RestaurentRepo;

@Service
public class RestaurantService {
	@Autowired
	private RestaurentRepo restRepo;
	
	// 1 add restaurent
	
	public ResponseStructure<Restaurent> saveRestaurent(Restaurent rest)
	{
		ResponseStructure<Restaurent> res = new ResponseStructure<Restaurent>();
		res.setMessage("One Restaurent got saved");
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData(restRepo.save(rest));
		return res;
	}
	
	// 2 add all Restaurent
	
	public ResponseStructure<List<Restaurent>> saveAllRestaurent(List<Restaurent> rest)
	{
		ResponseStructure<List<Restaurent>> res = new ResponseStructure<>();
		res.setMessage("All Restaurents got saved");
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData(restRepo.saveAll(rest));
		return res;
	}
	
	// 3 get All Restaurent
	
	public ResponseStructure<List<Restaurent>> getAllRestaurent()
	{
		ResponseStructure<List<Restaurent>> res = new ResponseStructure<List<Restaurent>>();
		List<Restaurent> rest = restRepo.findAll();
		if(rest.isEmpty())
		{
			throw new NoRecordAvailableException("NO Records available in the list");
		}else {
		res.setMessage("Get ALl the Restuarent");
		res.setStatusCode(HttpStatus.OK.value());
		res.setData(rest);
		return res;
		}
	}
	
	// get By id 
	
	public ResponseStructure<Restaurent> getById(Integer id)
	{
		ResponseStructure<Restaurent> res = new ResponseStructure<Restaurent>();
		Optional<Restaurent> opt = restRepo.findById(id);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("Id not found "+id);
		}else {
			res.setMessage("Record Available");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData(opt.get());
			return res;
		}
	}
	
	// update Restaurent
	
	public ResponseStructure<String> updateById(Restaurent rest)
	{
		ResponseStructure<String> res = new ResponseStructure<>();
		if(rest.getId()==null)
		{
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
			res.setMessage("Id Requuired to Update");
			res.setData("Failed");
			return res;
		}
		Optional<Restaurent> opt = restRepo.findById(rest.getId());
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("There is no record available by this id");
		}else {
			restRepo.save(rest);
			res.setMessage("Record found");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData("Successfull");
			return res;
		}
	}
	
	// update partially
	public ResponseStructure<String> updatePartially(Integer id , Map<String , Object> data)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Restaurent> opt =restRepo.findById(id);
		if(opt.isPresent())
		{
			Restaurent rest = opt.get();
			for(Map.Entry<String, Object> entry : data.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				switch (key)
				{
				case "name" : rest.setName((String)value);
				break;
				case "location" : rest.setLocation((String)value);
				break;
				case "rating" : rest.setRating((Double)value);
				break;
				}
			}
			restRepo.save(rest);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage(" Partially Updated Successfully");
			res.setData("Success");
			return res;
		}else {
			throw new IdNotFoundException("Id not Exist");
		}
	}
	
	// delete a record
	
	public ResponseStructure<String> deleteRecord(Integer id)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Restaurent> opt = restRepo.findById(id);
		if(opt.isEmpty())
		{
			throw new IdNotFoundException("Id not available");
		}else {
			restRepo.delete(opt.get());
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Succesfully deleted");
			res.setData("Successfull");
			return res;
		}
	}
	
	// find Restaurent By Loc
	
	public ResponseStructure<List<Restaurent>> getRestaurentByLocation(String location)
	{
		ResponseStructure<List<Restaurent>> res  = new ResponseStructure<>();
		List<Restaurent> list = restRepo.findRestaurentByLocation(location);
		if(list.isEmpty())
		{
			throw new NoRecordAvailableException("No Restaurent Available at this location");
		}else
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Restaurent at this location fetched");
			res.setData(list);
			return res;
		}
	}
	
	// getBy Rating Greater than a value
	
	public ResponseStructure<List<Restaurent>> getRestaurentGreaterThan(Double rating)
	{
		ResponseStructure<List<Restaurent>> res = new ResponseStructure<List<Restaurent>>();
		List<Restaurent> list = restRepo.findRestaurentByRatingGreaterThan(rating);
		if(list.isEmpty())
		{
			throw new NoRecordAvailableException("No Restaurent Having rating more than "+rating);
		}else
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Restaurent having rating greater than "+rating);
			res.setData(list);
			return res;
		}
	}
	// get Menu By restuarent
	
}
