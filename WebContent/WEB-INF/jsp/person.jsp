<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Training Person</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
</head>
<body ng-app="trainJeeApp" ng-controller="personController">
	<div class="container">
		<h3>Persons</h3>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Edit</th>
					<th>Name</th>
					<th>Birth Date</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="person in persons">
					<td>
						<button class="btn" ng-click="editPerson(person.id)">
							<span class="glyphicon glyphicon-pencil"></span> Edit
						</button>
						<button class="btn" ng-click="removePerson(person.id)">
							<span class="glyphicon glyphicon-remove"></span> Delete
						</button>
					</td>
					<td>{{ person.name }}</td>
					<td>{{ person.birthDate }}</td>
				</tr>
			</tbody>
		</table>
		<hr />
		<button class="btn btn-success" ng-click="createPerson()">
			<span class="glyphicon glyphicon-user"></span> Create New User
		</button>
		<hr />
		<h3 ng-hide="edit">Create New Person:</h3>
		<h3 ng-show="edit">Edit Person:</h3>
		
		<form class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">Name:</label>
			<div class="col-sm-10">
				<input type="text" ng-model="fName" placeholder="Name">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Date:</label>
			<div class="col-sm-10">
				<input type="text" ng-model="fDate" placeholder="Date">
			</div>
	 	</div>
		</form>
		
		<hr />
		<button class="btn btn-success" ng-disabled="error || incomplete" ng-click="submitForm()">
			<span class="glyphicon glyphicon-save"></span> Save Changes
		</button>
	</div>
	<script>
	angular.module('trainJeeApp', []).controller('personController', function($scope, $http) {
		refreshPage($scope);
		loadPersonList($scope, $http);
		
		$scope.editPerson = function(id) {
			var person = findPersonById($scope.persons, id);
			$scope.edit = true;
			$scope.fName = person.name;
			$scope.fDate = person.birthDate;
			$scope.id = person.id;
		};
		
		$scope.createPerson = function() {
			$scope.edit = false;
			$scope.fName = '';
			$scope.fDate = '';
			$scope.id = 0;
		};
		
		$scope.submitForm = function() {
			var sumbitActionUrl;
			var personInfo = getPersonFromForm($scope);
			if ($scope.edit) {
				submitActionUrl = "http://localhost:8080/train_jee/rest/person/edit";
			} else {
				submitActionUrl = "http://localhost:8080/train_jee/rest/person/add";
			}
			$http.post(submitActionUrl, personInfo).success(function(response) {
				refreshPage($scope);
				loadPersonList($scope, $http);
			});
		};
		
		$scope.removePerson = function(id) {
			// alert(JSON.stringify(id));
			
			$http.delete("http://localhost:8080/train_jee/rest/person/delete", {params: {id: id}})
				.success(function(response) {
					refreshPage($scope);
					loadPersonList($scope, $http);
				});
		}
		
		$scope.$watch("fName", function() {$scope.validate();});
		$scope.$watch("fDate", function() {$scope.validate();});
		
		$scope.validate = function() {
			var nameLength = $scope.fName.trim().length;
			$scope.incomplete = nameLength < 5 || nameLength > 50;
			$scope.error = isNaN(Date.parse($scope.fDate));
			
			if (!$scope.error) {
				var fdate = new Date($scope.fDate);
				var currentDate = new Date();
				
				$scope.error = fdate.getFullYear() < 1900
					|| fdate.getFullYear() >= currentDate.getFullYear();
			}
		}
	});
	
	var DATE_SEPARATOR = "/";
	
	function getPersonFrom(record) {
		var person = {};
		
		person.id = record.id;
		person.name = record.name;
		person.birthDate = getDateToString(new Date(record.birthDate));
		
		return person;
	}
	
	function getPersonFromForm($scope) {
		var person = {};
		person.id = $scope.id;
		person.name = $scope.fName;
		person.birthDate = Date.parse($scope.fDate);
		return person;
	}
	
	function getDateToString(date) {
		var dd = date.getDate();
		var mm = date.getMonth() + 1;
		var yyyy = date.getFullYear();
		
		var dateAsString = "";
		if (mm < 10) {
			dateAsString += "0";
		}
		dateAsString += mm + DATE_SEPARATOR;
		if (dd < 10) {
			dateAsString += "0";
		}
		dateAsString += dd + DATE_SEPARATOR + yyyy;
		return dateAsString;
	}
	
	function refreshPage($scope) {
		$scope.edit = false;
		$scope.fName = '';
		$scope.fDate = '';
		$scope.id = 0;
		$scope.persons = [];
	}
	
	function loadPersonList($scope, $http) {
		$http.get("http://localhost:8080/train_jee/rest/person/get")
			.success(function(response) {
				for (var i = 0; i < response.records.length; i++) {
					var record = response.records[i];
					$scope.persons[i] = getPersonFrom(record);
				}
			});
	}
	
	function findPersonById(persons, id) {
		for (var i = 0; i < persons.length; i++) {
			if (persons[i].id == id) {
				return persons[i];
			}
		}
		return null;
	}
	</script>
</body>
</html>